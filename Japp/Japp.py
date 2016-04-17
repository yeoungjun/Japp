#-*- coding: utf-8 -*-
__author__ = "Starleaguer (yeoung_jun@naver.com)"

import argparse
import jUtils

UPDATE = '2015-03-14'
VERSION   = '0.01'

class Japp:
	def __init__(self):
		self.util = jUtils.Utils()
		return

	def __del__(self):
		return

	def Tab_to_space(self,file,n):
		fd = open(file)
		msg = fd.read()
		fd.close()
		changed = msg.replace("\t"," "*n)
		self.util.makeFile(file,changed)

	def Print_String_Infomation(self, str):
		print "String:", str
		print "Length:", len(str)
		print "MD5:",self.util.data_hash('md5',str)
		print "SHA:",self.util.data_hash('sha',str)
		print "SHA256:",self.util.data_hash('sha256',str)
		print "ROT13:\n",self.util.rot13(str)


def PrintLogo():
	logo = "Japp (Update: %s, Version: %s)\n"
	s = logo % (UPDATE , VERSION)
	print s

def PrintUsage():
	use_string = \
		'''Useage:
		        Tap to Space       : -tts [file] -n [n space]
		        Print String Info  : -psi [string]
		        Help               : -h
		'''
	print use_string

def DefineOptions():
	parser = argparse.ArgumentParser()
	parser.add_argument("-f", help = "file name")
	parser.add_argument("-n",type=int, default = 1,help = "number")
	parser.add_argument("-tts", help = "Tap to Space")
	parser.add_argument("-psi", help = "Print String Information")
	return parser


def Main():
	PrintLogo()
	parser = DefineOptions()
	args = parser.parse_args()
	app = Japp()

	if args.tts:
		app.Tab_to_space(args.tts, args.n)

	elif args.psi:
		app.Print_String_Infomation(args.psi)

	else:
		PrintUsage()
		return 0

if __name__=="__main__":
	Main()