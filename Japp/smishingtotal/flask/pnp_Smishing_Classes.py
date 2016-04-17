#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import sys
import re
import zipfile
import pnp_Smali
import pnp_Dex2jar
import pnp_Smishing_Decompiler


reload(sys)
sys.setdefaultencoding('utf-8')


def Search_File(file_path,sig,):

	result =[]
	res = []

	fp = open(file_path,"rb")
	data = fp.read()
	fp.close()
   
	ptr1 = 0
	IPs = re.findall('\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}',data)

	data = data.encode("hex")
	output = []
	
	while data.find(sig,ptr1) > 0:
		ptr1 =  data.find(sig,ptr1)
		ptr2 = ptr1
		while data.find("00",ptr2) % 2 != 0:
			ptr2 += 1
		ptr2 =	 data.find("00",ptr2)
		output.append( data[ptr1:ptr2].decode("hex"))
		ptr1 += 2


	for out in set(output):
		#print socket.gethostbyname(out[out.find("://")+3:])
		#print out
		result.append( "HOST: "+out)
	if (len(IPs) == 0) :
		pass
		#print "# IP Not Found"
	else:
		for ip in set(IPs):
			#print ip
			res.append( "IP: "+ip )
			#whois.Whois(ip)
			#webbrowser.open_new("http://www.geoiptool.com/en/?IP="+ip)

	result += res
	return result

def Classes_file(malware_path, malware_Dir,malware_md5):

	result =[]
	apkprotect = 0 	# 파일이 apk protect로 난독화 유무를 판별하기 위해 사용한다.
	classesfile_path =""
	Zip_Files = zipfile.ZipFile(malware_path, 'r') # 압축되어진 파일을 읽는다.

	for File in Zip_Files.namelist(): # 압축되어진 파일들을 불러온다.

		if File.find("classes.dex") == 0:					# 만약 classes.dex 파일이 있다면 (파일 유무를 확인)
			name = File.rfind("/")							# 이미지의 이름을 짜르기 위해서 뒷 부분에서 부터 / 문자가 나온 곳 까지 찾아서 name에 넣는다. (classes.dex의 이름을 넣는다.)							
			data = Zip_Files.read(File)						# 압축되어진 파일을 읽는다.
			classesfile_path = malware_Dir+File[name+1:]	# 새로 만들 파일의 경로를 구한다.(md5 hash 값으로 된 경로 + 파일의 이름(classes.dex))
			fw = open(classesfile_path,'wb')				# 새로 만들 파일을 열어 둔다.
			fw.write(data)									# 파일을 쓴다.
			fw.close()										# 파일을 닫는다.														

		if File.find("apkprotect.com") == 0: # apkprotect.com 이라는 폴더가 있다면 -> 폴더가 존재하면 apkprotect로 난독화가 되어 있는 것이다.
			apkprotect = 1				# apk 파일이 존재한다는 것을 확인한다.
			result.append( "Apkprotect detecton!! ")
	
	
	if bool(apkprotect):
		temp_folder = "temp"
		pnp_Smali.Bsmali(classesfile_path,temp_folder)
		pnp_Smali.Ssmali(classesfile_path,temp_folder)

	result += Search_File(classesfile_path,"687474703a2f")

	ret = pnp_Dex2jar.Dex2jar(malware_Dir,malware_md5,classesfile_path)
	if (ret == True):
		pnp_Smishing_Decompiler.Decompiler(self.malware_Dir)									# 압축이 풀린 스미싱 파일을 디컴파일 하기 위해 사용을 한다.
	else :
		pass
	
	return result
