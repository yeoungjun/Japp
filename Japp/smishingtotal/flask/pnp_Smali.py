#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import sys
import os
import shutil
import commands
reload(sys)
sys.setdefaultencoding('utf-8')


def Bsmali(classesfile_path,temp_folder):
	cmd = "java -jar bsmali.jar -o "+temp_folder+" "+classesfile_path
#	os.system(cmd)
	fail , output = commands.getstatusoutput(cmd)

	if (fail):
		print "Bsmali faild"
		pass
	else:
		for p, ds, fs in os.walk(temp_folder):
			for f in fs:
				if os.path.join(f).find("a.smali") == 0:
					os.remove(os.path.join(p, f))

def Ssmali(classesfile_path,temp_folder):
	cmd = "java -jar ssmali.jar "+temp_folder+" -o "+classesfile_path
	#os.system(cmd)
	fail , output = commands.getstatusoutput(cmd)

	if (fail):
		pass
	else:	
		temp =  os.path.join(os.getcwd(),temp_folder)
		shutil.rmtree(temp)
