#-*- coding:utf-8 -*-
import re
import os
import sys
import urllib2
import argparse
from bs4 import BeautifulSoup

def getGoogleurl(search,nextPage,siteurl=False):
    if siteurl==False:
        return 'http://www.google.com/search?q='+urllib2.quote(search)+'&start=%d' % nextPage
    else:
        return 'http://www.google.com/search?q=site:'+urllib2.quote(siteurl)+'%20'+urllib2.quote(search)


def getNaverurl(search,nextPage,siteurl=False):
    if siteurl==False:
        return 'http://search.naver.com/search.naver?query='+urllib2.quote(search)#+'&start=%d' % nextPage
    else:
        return 'http://www.google.com/search?q=site:'+urllib2.quote(siteurl)+'%20'+urllib2.quote(search)


def getgooglelinks(search,nextPage,siteurl=False):
   headers = {'User-agent':'Mozilla/11.0',
       'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
       'Accept-Charset': 'ISO-8859-1,utf-8;q=0.7,*;q=0.3',
       'Accept-Encoding': 'none',
       'Connection': 'keep-alive'}
   req = urllib2.Request(getGoogleurl(search,nextPage,siteurl),None,headers)
   site = urllib2.urlopen(req)
   data = site.read()
   site.close()

   start = data.find('<div id="res">')
   end = data.find('<div id="foot">')
   if data[start:end]=='':
      return False
   else:
      links =[]
      data = data[start:end]
      start = 0
      end = 0        
      while start>-1 and end>-1:
          if siteurl==False:
              start = data.find('<a href="/url?q=')
          else:
              start = data.find('<a href="/url?q='+str(siteurl))

          data = data[start+len('<a href="/url?q='):]
          end = data.find('&amp;sa=U&amp;ei=')
          
          if start>-1 and end>-1: 
              link =  urllib2.unquote(data[0:end])
              data = data[end:len(data)]
              if link.find('http')==0:
                  links.append(link)
      return links

def getNaverlinks(search,nextPage,siteurl=False):
   headers = {'User-agent':'Mozilla/11.0',
       'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
       'Accept-Charset': 'ISO-8859-1,utf-8;q=0.7,*;q=0.3',
       'Accept-Encoding': 'none',
       'Connection': 'keep-alive'}
   req = urllib2.Request(getNaverurl(search,nextPage,siteurl),None,headers)
   site = urllib2.urlopen(req)
   data = site.read()
   site.close()


   links =[]
   start = 0
   end = 0        
   while start>-1 and end>-1:
      if siteurl==False:
          start = data.find('<span class="inline"> <a href="')
      else:
          start = data.find('<a href="/url?q='+str(siteurl))

      data = data[start+len('<span class="inline"> <a href="'):]
      end = data.find('" ')
      
      if start>-1 and end>-1: 
          link =  urllib2.unquote(data[0:end])
          data = data[end:len(data)]
          links.append(link)
   return links


def googleSearch(query,page):
  for i in range(page):
    links = getgooglelinks(query,i*10)
    for link in links:
           print link
def naverSearch(query,page):
  for i in range(page):
    links = getNaverlinks(query,i*10)
    for link in links:
           print link

googleSearch('김태희',2)
naverSearch('김태희',2)

