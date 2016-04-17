#-*- coding: utf-8 -*-
import os
import sys
import time
import win32con
import win32evtlog
import win32evtlogutil
import winerror
reload(sys)
sys.setdefaultencoding('utf-8')



class LOG_SOLUTION:
#----------------------------------------------------------
# __init__
# _path	  :	로그가 저장되는 경로
# logTypes:	윈도우 로그 타입종류
# update  : 새로운 로그가 있는지 확인 
#----------------------------------------------------------
	def __init__(self):
		self._server = "localhost"
		#------------------ test -----------------------
		self._path = "C:\Users\Administrator\Desktop\Tmp"
		#------------------ test -----------------------

		self.logTypes = ["System", "Application", "Security"]
		self.data = ""
		self.update ={"System":0,"Application":0,"Security":0}
#----------------------------------------------------------
# CreateLogFile 
# 윈도우 이벤트 로그를 수집&가공 _path 폴더에 저장
# ( Time: / ID: / Type: / Record: / Source: / Message: )
#----------------------------------------------------------
	def CreateLogFile(self):
		for logtype in self.logTypes:
			line_break = '-' * 80+'\n'
			hand = win32evtlog.OpenEventLog(self._server,logtype)
			total = win32evtlog.GetNumberOfEventLogRecords(hand)

			#새로운 로그가 있는지 확인
			if self.update[logtype] != total:
				path = os.path.join(self._path, "%s.log" % logtype)
				if os.path.isfile(path):
					fw = open(path,'a')
				else:
					fw = open(path,'w')
				
				flags = win32evtlog.EVENTLOG_BACKWARDS_READ|win32evtlog.EVENTLOG_SEQUENTIAL_READ
				events = win32evtlog.ReadEventLog(hand,flags,0)
				evt_dict={win32con.EVENTLOG_AUDIT_FAILURE:'EVENTLOG_AUDIT_FAILURE',
						  win32con.EVENTLOG_AUDIT_SUCCESS:'EVENTLOG_AUDIT_SUCCESS',
						  win32con.EVENTLOG_INFORMATION_TYPE:'EVENTLOG_INFORMATION_TYPE',
						  win32con.EVENTLOG_WARNING_TYPE:'EVENTLOG_WARNING_TYPE',
						  win32con.EVENTLOG_ERROR_TYPE:'EVENTLOG_ERROR_TYPE'}

				try:
					Out = ''
					events = 1
					
					for i in range(total - self.update[logtype]):
						events=win32evtlog.ReadEventLog(hand,flags,0)
						for ev_obj in events:
							Time = ev_obj.TimeGenerated.Format() #'12/23/99 15:54:09'
							ID = str(winerror.HRESULT_CODE(ev_obj.EventID))
							computer = str(ev_obj.ComputerName)
							cat = ev_obj.EventCategory

							record = ev_obj.RecordNumber
							Message = win32evtlogutil.SafeFormatMessage(ev_obj, logtype)
							Source = str(ev_obj.SourceName)
							if not ev_obj.EventType in evt_dict.keys():
								Type = "unknown"
							else:
								Type = str(evt_dict[ev_obj.EventType])
							
							fw.write( "Time:"	+ Time		 +"\n")
							fw.write( "ID:"		+ ID		 +"\n")
							fw.write( "Type:"	+ Type		 +"\n")
							fw.write( "Record:"	+ str(record)+"\n")
							fw.write( "Source:"	+ Source	 +"\n")
							fw.write( "Message:"+ Message	 +"\n")
							fw.write( line_break				  )
							

				except:
					print traceback.print_exc(sys.exc_info())
			 
				print "Success"
				self.update[logtype] = total
				fw.close()
			else:
				print logtype,"No update"
				pass

#----------------------------------------------------------
# ReadLogFile
# Path에 저장되어있는 로그를 불러옴
#----------------------------------------------------------
	def ReadLogFile(self):
		for logtype in self.logTypes:
			path = os.path.join(self._path, "%s.log" % logtype)
			fr = open(path)
			self.data = fr.read()
			fr.close()
			return self.data

#----------------------------------------------------------
# Filtering
# F_Type		= 윈도우 이벤트 로그타입 지정
# F_Attribute	= 속성
# F_White		= 화이트 필터
# F_Black		= 블랙필터
#----------------------------------------------------------
	def Filtering(self):
	#def Filtering(self,F_Tpye,F_Attribute,F_White,F_Black):
		#------------------ test -----------------------
		F_Type = "System"#str(raw_input("Filter What? "))
		F_Attribute = "Type"#str(raw_input("Type: "))
		F_White = "EVENTLOG_ERROR_TYPE"#str(raw_input("White: "))
		#F_Black = str(raw_input("Black: "))
		#------------------ test -----------------------

		path = os.path.join(self._path, "%s.log" % F_Type)
		fr = open(path)
		self.data = fr.read()
		st = 0
		po = 0
		en = 0
		result =''
		while po >= 0:
			st = self.data.find('Time',st)
			po = self.data.find(F_Attribute,st)
			enter = self.data.find('\n',po)
			en = self.data.find('-'*80,po)
			if self.data[po:enter].find(F_White) > 0:
				result += self.data[st:en]
			st = po+1

		fr.close()
		return result

	def Print(self):
		pass


	def Exit(self):
		pass


a = LOG_SOLUTION()

a.CreateLogFile()

Log = a.ReadLogFile()
#print Log

print a.Filtering()