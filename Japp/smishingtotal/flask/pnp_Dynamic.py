#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import time
from subprocess import Popen, PIPE

def Droidbox(malware_path,md5_path) :

	emulP = Popen(['emulator',"-avd","practiceandroid4.3","-system","/opt/android/adt-bundle-linux-x86_64-20130729/DroidBox/images/system.img","-ramdisk","/opt/android/adt-bundle-linux-x86_64-20130729/DroidBox/images/ramdisk.img","-wipe-data","-prop","dalvik.vm.execution-mode=int:portable &"])

	time.sleep(60)

	droidP = Popen(["python","/opt/android/adt-bundle-linux-x86_64-20130729/DroidBox/scripts/droidbox.py", "/flask"+malware_path[1:],"/flask"+md5_path[1:]+"droid-result.txt","100"])
	time.sleep(5)	
	sharkP = Popen(["tshark", "-d","tcp.port==5554,http","-w",md5_path+"packets.pcap"])

	time.sleep(100)

	droidP.kill()
	emulP.kill()
	sharkP.kill()

	return 0