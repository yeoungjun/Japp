#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import sys
import zipfile

reload(sys)
sys.setdefaultencoding('utf-8')	# 한글을 입력 받을 수 있도록 한다.

def Image_file(malware_path, malware_Dir):

		cnt = 0 # 카운트를 세기위한 임시 변수
		Zip_Files = zipfile.ZipFile(malware_path, 'r')									# 파일의 압축을 풀어 준다.
		for File in Zip_Files.namelist():												# 압축이 풀린 파일들을 불러 온다.
			if File.find('.png') > 0 or File.find('.jp') > 0 or File.find('.bmp') > 0 or File.find('.gif') > 0:				# 확장자기 png / jp / bmp로 되있는 것들을 찾는다.
				cnt += 1																# 찾으면 카운터를 증가 시킨다.
				name = File.rfind("/")													# 이미지의 이름을 짜르기 위해서 뒷 부분에서 부터 / 문자가 나온 곳 까지 찾아서 name에 넣는다. 
				data = Zip_Files.read(File)											# 압축된 이미지 파일을 읽는다.
				fw = open(malware_Dir+str(cnt)+"_"+File[name+1:],'wb')						# 이미지의 경로와 이미지 이름과 더해서 파일을 열게 한다.
				fw.write(data)														# 이미지 파일을 쓴다.
				fw.close()															# 이미지 파일을 닫는다.