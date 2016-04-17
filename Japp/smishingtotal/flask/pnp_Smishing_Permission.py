#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import sys
import zipfile
import os

reload(sys)
sys.setdefaultencoding('utf-8')

def Permission_write(data,malware_Dir) :

    pnp_wirte_smishing_information = open(malware_Dir + "File Information.txt",'a')			# file information.txt를 열어서 퍼미션들을 추가한다.
    pnp_wirte_smishing_information.write("[*]"+data)									# data를 가져와서 추가한다.
    pnp_wirte_smishing_information.close()													# 파일을 닫는다.

def Permission_file(malware_path,malware_Dir):

	result =[]
	result.append("### Android Permission ###")
	Zip_Files = zipfile.ZipFile(malware_path, 'r')				# 압축되어있는 파일들을 읽어 온다.
	for File in Zip_Files.namelist():							# 압축된 파일들을 하나씩 불러온다
		if File.find("AndroidManifest.xml") == 0 :				# 파일중에 AndroidMainfest.xml 파일을 찾는다.
			name = File.rfind("/")								# xml 파일의 이름을 짜르기 위해서 뒷 부분에서 부터 / 문자가 나온 곳 까지 찾아서 data에 넣는다. 
			data = Zip_Files.read(File)							# xml 파일을 읽어 데이타에 넣는다.
			fw = open(malware_Dir+File[name+1:],'wb')			# xml 파일을 연다.
			fw.write(data)										# 파일을 쓴다.
			fw.close()											# 파일을 닫는다.
	permission = "61006e00640072006f00690064002e007000650072006d0069007300730069006f006e" # 퍼미션을 시그니쳐로 등록을 한다.
	a = 1																# 첫 부분
	data = data.encode("hex")											# xml 파일을 헥사값으로 인코딩 한다.
	if data.find(permission) > 0:										# 특정 시그니쳐가 있는지 확일을 한다.
		while data.find(permission,a) > 0:								
			a =  data.find(permission,a)								# 첫 부분부터 퍼미션이 있는 곳까지 시그니쳐를 찾는다.
			b =	 data.find("0000",a)									# 첫 부분부터 0000(NULL)이 있는 곳까지 찾는다. 
			result.append(data[a:b].decode("hex")[::2])
			Permission_write(data[a:b].decode("hex")[::2],malware_Dir)	# 퍼미션 모든 내용을 쓰게 된다.
			a += 1
	return result






