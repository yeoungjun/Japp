#-*- coding: utf-8 -*-

import pymysql
import sys
#########################
DATABASE = "DB"
HOST = "127.0.0.1"
PORT = "34"
USER = "admin"
PASSWD = "admin"

class Database(object):
	
	def __init__(self):
		self.db_file = DATABASE
		self.db_host = HOST
		self.db_port = PORT
		self.db_user = USER
		self.db_passwd = PASSWD
		return

	def __del__(self):
		return

	def init(self):
		self.db = pymysql.connect(host = self.db_host, port = self.db_port, user = self.db_user, passwd = self.db_passwd, db = self.db_file)
		self.cursor = self.db.cursor()
		return

	def free(self):
		self.cursor.close()
		self.db.close()
		return

	def execute(self, sql):
		self.init()
		self.cursor.execute(sql)
		self.db.commit()
		return

	def sqlinjection_protection(self, data):
		data = data.replace("'", "\\'")
		return data
