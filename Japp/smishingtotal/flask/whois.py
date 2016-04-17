#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import re
import sys
import urllib
import webbrowser
reload(sys)
sys.setdefaultencoding('utf-8')

def Whois(ip):
	url = "http://whois.kisa.or.kr/kor/whois.jsc?query="+ip+"&ip=1.1.1.1"
	response = urllib.urlopen(url)
	data = response.read()
	result = data.encode('euc-kr')

	result = re.sub('<.*>','',result)
				
	a = result.find('}')
	b = result.find('document.oncontextmenu')
				
	map_a = result.lower().rfind('address')
	map_b = result[map_a:].find(':') + 1
	map_c = result[map_a:].find('\n')

	map_addr = result[map_a+map_b:map_a+map_c].strip()
	webbrowser.open_new("https://www.google.co.kr/maps/place/"+map_addr)
	