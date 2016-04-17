#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import sys
import os
import pnp_Hash
import pnp_Smishing_Image
import pnp_Smishing_Classes
import pnp_Smishing_Permission
import pnp_Smishing_information
import pnp_Smishing_CheckZip
import pnp_Dynamic
import pnp_Smishing_API
from multiprocessing import Process

reload(sys)
sys.setdefaultencoding('utf-8') # 한글을 입력 받을 수 있도록 한다.


class Smishing_Analysis():

	# 경로를 정의 한다.
	def init(self,malware):
		self.malware_path = malware 									# 파일의 경로를 알아 낸다.
		hash_ret, self.malware_md5 = pnp_Hash.Md5_file(self.malware_path)			# 경로를 md5 hash와 시킨다 -> 파일의 중복성을 피하기 위해서 사용한다.
		if (hash_ret):
			self.malware_test = "./backup/"									# backup 폴더 위치를 변수로 사용한다.
			self.malware_Dir = self.malware_test + self.malware_md5 + "/"	# backup 폴더 + 경로의 md5 hash를 이용하여 경로를 변수로 사용한다. -> 정적 분석한 결과 값을 넣기 위한 폴더의 경로를 사용한다.
			return True
		else:
			return False
			


	#엔진부분
	def Engine(self):
		result =[]
		result.append("MD5: "+self.malware_md5)
		
		if not os.path.isdir(self.malware_test): 		# 만약에 backup 폴더가 없을 경우에는
			os.mkdir(self.malware_test)			# backup 폴더를 생성한다.
		if not os.path.isdir(self.malware_Dir):		# 결과 폴더가 없을 경우에는
			os.mkdir(self.malware_Dir)				# 결과 폴더를 생성한다.
			#  !! make result txt file

		
		pnp_Smishing_CheckZip.HexCheck(self.malware_path)										# 암호화 위장된 파일을 검사하기 위해 사용을 한다.
		#pnp_Smishing_information.Information_Print(self.malware_path,self.malware_Dir)			# 스미싱 파일의 정보를 출력하기 위해 사용을 한다.
		pnp_Smishing_Image.Image_file(self.malware_path,self.malware_Dir)						# 스미싱 파일의 이미지들을 저장한다.
		result += pnp_Smishing_Permission.Permission_file(self.malware_path,self.malware_Dir)				# 스미싱 파일의 퍼미션을 확인하기 위해 사용을 한다.
		result += pnp_Smishing_Classes.Classes_file(self.malware_path,self.malware_Dir,self.malware_md5)	# 스미싱 파일의 Classes 파일을 확인하고 정보를 알아내기 위해 사용된다. (apk protect로 난독화 되어잇으면 풀어주고 C&C ip를 찾는다.)
		result += pnp_Smishing_API.FindApi(self.malware_Dir )
		return result

	def DynamicEngine(self):
		pnp_Dynamic.Droidbox(self.malware_path,self.malware_Dir)
  		return 0

# 메인 (시작 부분)
if __name__ == "__main__":

	# 메인 초기 정보
	print "========================================================================="
	print "[RNI Security] Smishint Stoker (for Linux) Version 0.1 Date Jun.12.2014"
	print "Copyright (C) 2014 Developer Starleaguer & R3d5h4rk & Dabster.           "
	print "All right reserved."
	print "========================================================================="

	if len(sys.argv) is 1:
		input_file = str(raw_input("file name? "))	# 파일을 이름을 입력 받는다.
	else :											# 예외처리
		input_file = sys.argv[1]


	if not os.path.isfile(input_file):							# 파일이 있는지 확인 (없으면)
		print 'Not found file..'								# 파일이 없다고 출력
	else:														# 파일이 있으면
		start = Smishing_Analysis()
		ret = start.init(input_file)
		if (ret==True):
			start.Engine()											# Smishing_Analysis의 Engine 함수를 호출 한다.
			input_file = str(raw_input("=== press any key ==="))	# 마지막 출력 값

		else:
			print "Error Init"
			exit()