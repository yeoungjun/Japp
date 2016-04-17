import time
from subprocess import Popen, PIPE

md5_path = "./"
#p = Popen(['emulator',"-avd","practiceandroid4.3","-system","/opt/android/adt-bundle-linux-x86_64-20130729/DroidBox/images/system.img","-ramdisk","/opt/android/adt-bundle-linux-x86_64-20130729/DroidBox/images/ramdisk.img","-wipe-data","-prop","dalvik.vm.execution-mode=int:portable &"])
#pro = Popen(["tshark", "-d","tcp.port==5554,http","-w",md5_path+"packets.pcap"])

#time.sleep(60)

#malware_path = "./upload/ko.zip"
#p = Popen(["python","/opt/android/adt-bundle-linux-x86_64-20130729/DroidBox/scripts/droidbox.py", "/flask"+malware_path[1:],"/flask"+md5_path[1:]+"droid-result.txt","100"])
sharkp = Popen(["tshark", "-d","tcp.port==5554,http","-w",md5_path+"packets.pcap"])
time.sleep(10)
sharkP.kill()

#p.kill()

