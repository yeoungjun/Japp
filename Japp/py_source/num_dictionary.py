import string

fp = open("dictionary.txt","wb")

num = 0
buff = ""
while 1:
	if num<1000000:
		num += 1
		buff +=  str(num).zfill(8) + "\x0a"

	else:
		break
buff = buff[::-1]
fp.write(buff)
fp.close()