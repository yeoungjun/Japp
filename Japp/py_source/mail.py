import smtplib
import getpass
from email.mime.text import MIMEText

ID = raw_input("Gmail ID: ")
PASSWD = getpass.getpass("PW: ")

mailID = ID+"@gmail.com"
recv = raw_input("To: ")
textfile = raw_input("File: ")


fp = open(textfile, 'rb')
msg = MIMEText(fp.read())
fp.close()

msg['Subject'] = '%s' % textfile
msg['From'] = mailID
msg['To'] = recv

s = smtplib.SMTP_SSL('smtp.gmail.com',465)
s.login(ID, PASSWD)
s.sendmail(mailID, recv, msg.as_string())
s.quit()