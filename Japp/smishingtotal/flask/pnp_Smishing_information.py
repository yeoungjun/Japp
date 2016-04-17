#-*- coding: utf-8 -*-
__author__ = 'Starleaguer & R3d5h4rk & Dabster'

import zipfile
import os
import sys
import pnp_Hash

def Information_Print(malware_path, malware_Dir) :

    pnp_smishing_informaiton_list = []  # 스미싱 파일의 정보들을 저장하기 위한 리스트

    # apk / zip 파일을 이름, 사이즈, 크기, 경로, md5 hash 값, sha1 hash 값, sha256 값을 list에 넣어둔다.
    pnp_smishing_informaiton_list.append("[*] Name: " + malware_path + "\n"+ "- Size: " + str(os.path.getsize(malware_path)) + "Bytes" + "\n" + "- Path: "
                                         + os.getcwd() + "\n" + "- MD5 Hash: " + pnp_Hash.Md5_file(malware_path) + "\n"
                                         + "- SHA1 Hash: " + pnp_Hash.Sha_file(malware_path) + "\n" + "- SHA256 Hash: " + pnp_Hash.Sha256_file(malware_path) + "\n" )

    pnp_malware_file = zipfile.ZipFile(malware_path,'r') # 압축된 파일을 압축을 풀게 된다.

    for pnp_malware_file_name in pnp_malware_file.namelist() :
        # 압축이 풀린 파일들을 모두 이름, 사이즈, 크기, 경로, md5 hash 값, sha1 hash 값, sha256 값을 list에 넣어둔다.
        pnp_smishing_informaiton_list.append("[*] Name: " + pnp_malware_file_name + "\n" + "- Size: " + str(os.path.getsize(malware_path)) + "Bytes"
                                             + "\n" + "- Path: " + os.getcwd() +"\n" + "- MD5 Hash: " + pnp_Hash.Md5_file(malware_path) + "\n"
                                         + "- SHA1 Hash: " + pnp_Hash.Sha_file(malware_path) + "\n" + "- SHA256 Hash: " + pnp_Hash.Sha256_file(malware_path) + "\n")

    # 리스트에 있는 정보들을 txt 파일에 저장하기 위해서 사용한다.    
    for pnp_smishing_information_print in pnp_smishing_informaiton_list :               # 파일의 정보를 하나씩 불러온다.
        pnp_wirte_smishing_information = open(malware_Dir + "File Information.txt",'a') # file information.txt를 생성하고 열게 된다.
        pnp_wirte_smishing_information.write(pnp_smishing_information_print+'\n')       # 파일의 정보를 쓰게 된다.

    pnp_wirte_smishing_information.close()  # 파일을 닫는다.
    