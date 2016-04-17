#-*- coding: utf-8 -*-

from multiprocessing import Process
import os
 
def info(title):
    print title
    print 'module name:', __name__
    #print 'parent process:', os.getppid() UNIX에서만 가능
    print 'process id:', os.getpid()
 
def f(name):
    info('function f')
    print 'hello', name
 
if __name__ == '__main__':
    info('main line')
    p = Process(target=f, args=('jang',))
    p.start()
    p.join()