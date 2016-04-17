#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import md5
#import sha
#import _sha256

# MD5 Hash
def Md5_file(file) :
	try:
		f = open(file,"r")
		f_data = f.read()
		f.close()
		return True, md5.md5(f_data).hexdigest()

	except :
		return False , " "


# # SHA1 Hash
# def Sha_file(file) :
#     f = open(file,"r")
#     f_data = f.read()
#     f.close()
#     return sha.sha(f_data).hexdigest()

# # SHA256 Hash
# def Sha256_file(file) :
#     f = open(file,"r")
#     f_data = f.read()
#     f.close()
#     return _sha256.sha256(f_data).hexdigest()



# def Extract(self):
# 	zip_location = (self.malware_path)
# 	fzip = zipfile.ZipFile(zip_location, 'r')
# 	fzip.extractall(path='./test/')
# 	fzip.close()
