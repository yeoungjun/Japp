#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import os

def HexCheck(zipfile): 			# 파일을 받아옵니다.

	fr = open(zipfile,'rb')			# 파일을 열게 된다.
	data = fr.read()				# 파일을 읽어 data에 넣는다.
	fr.close()					# 파일을 닫는다.

	sig = "14000908" 				# 특정 시그니쳐 -> 압축 포맷구조에서 암호화 부분 
	data = data.encode("hex") 		# data를 헥사 값으로 인코딩 한다.
	ptr1 = 0 						# 위치 값을 찾기 위한 임시 포인터
	data_check = 0 				# 데이터가 변경되었는지 확인하기 위해 사용한다.

	while data.find(sig,ptr1) >= 0:					# 첫번째 포인터 부터 특정 시그니쳐가 있는 곳까지 찾는다.
		ptr1 = data.find(sig,ptr1)					# 14000908의 첫부분을 포인터로 지정하게 된다.
		ptr2 = ptr1+4								# 그리고 오른쪽으로 4번 이동하여 0908의 0을 두번째 포인터로 지정하게 된다.
		if data[ptr2:ptr2+4] == "0908":				# 두번째 포인터에서 4자리 수를 읽고 0908이랑 같으면 
			data_check = 1 						# 0908을 찾게 되면 체크를 하게 된다.
			data = data[:ptr2]+"0808"+data[ptr2+4:] 	# 14000908의 앞부분을 그대로 사용하고 0908을 0808로 변경을 하고 뒷부분 이어 붙이게 된다. 
		ptr1 += 1 									# 그리고 오른쪽으로 포인터를 옮겨 다시 읽는 것을 방지한다.


	if data_check:
		data = data.decode("hex") 							# 데이터를 헥사 값으로 디코딩 시켜준다.
		os.rename(zipfile,zipfile+"fake_passwd_origin")		# 원본 바이너리를 그대로 두기 위해서 백업을 시켜놓는다. (뒷부분에 origin으로 판별을 한다.)
		fw = open(zipfile,'wb')								# 변경된 데이터를 쓰기 위해 파일을 열게 된다.
		fw.write(data)									# 데이터를 쓴다.
		fw.close()										# 파일을 닫는다.

