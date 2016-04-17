#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import sys
import os
import shutil
import commands
reload(sys)
sys.setdefaultencoding('utf-8')


def FindApi(Dirmalware_path):
	result =[]
	dbs=[]
	fd_db = open("API.db","r")
	db_lines = fd_db.readlines()
	for line in db_lines:
		dbs.append(line[:-1])
	fd_db.close()

	print dbs

	path = Dirmalware_path +"decom_source"
	print path
	for p, ds, fs in os.walk(path):
		for f in fs:
			fd = open(os.path.join(p, f),"r")
			lines = fd.readlines()
			#data = fd.read()
			for db in dbs:
				for line in lines :
					if (line.lower().find(db)) == 0 :
						result.append("("+os.path.join(f)+") "+line)
			fd.close()
	return result