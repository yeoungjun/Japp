#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import os
import zipfile
import commands
#from subprocess import Popen, PIPE


def Decompiler(smalware_Dir) :

	zip_location = (smalware_Dir + "classes_dex2jar.jar")		# 압축이 풀린 경로에서 Classes_dex2jar.jar 파일의 위치를 찾는다.
	fzip = zipfile.ZipFile(zip_location, 'r')					# jar 파일을 읽어 온다.
	fzip.extractall(path=smalware_Dir+"classes")				# classes 파일을 압출을 푼다.
	fzip.close()												# 파일을 닫는다.

	cmd = "./jad -o -r -sjava -d"+smalware_Dir+"/decom_source "+smalware_Dir+"classes/**/*.class" # 디컴파일을 명령어를 지정한다.
	fail , output = commands.getstatusoutput(cmd)

	#p = Popen(cmd , shell=True, stdout=PIPE, stderr=PIPE)										# 명령어를 실행한다.
	
	#out, err = p.communicate()
	#os.system(cmd)
	"""
	if p.returncode:
		print "Success Decompiler!"
	else:
		print "fail Decompiler"
	"""