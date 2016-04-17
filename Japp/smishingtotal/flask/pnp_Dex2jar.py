#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import os
import commands
#from subprocess import Popen, PIPE

def Dex2jar(malware_dir,malware_md5,classesfile_path):
	
	cmd = "sh ./dex2jar_sh/dex2jar.sh "+classesfile_path					# 입력할 명령어를 지정해 준다(dex2jar+classes.dex 파일의 경로)
	#p = Popen(cmd , shell=True, stdout=PIPE, stderr=PIPE)	# cmd 명령어를 실행한다.
	#os.system(cmd)
	fail , output = commands.getstatusoutput(cmd)

	if (fail):
		return False
	else:
		True
	#out, err = p.communicate()								
	"""
	if p.returncode:										# 동작이 잘 되면
		print "Success dex2jar!"
	else:												# 동작이 잘 되지  으면
		print "fail dex2jar"
	"""
	#os.system(cmd)
	#shutil.copy("./classes_dex2jar.jar",malware_dir)
	#temp =  os.path.join(os.getcwd(),"classes_dex2jar.jar")
	#os.remove("./classes_dex2jar.jar")
	#os.remove("./classes.dex")


