import RPi.GPIO as GPIO
import time
import sturct
from bluetooth import *
pin = 18

TRIG = 23
ECHO = 24

GPIO.setmode(GPIO.BCM)
GPIO.setup(pin, GPIO.OUT)
GPIO.setup(TRIG,GPIO.OUT)
GPIO.setup(ECHO,GPIO.IN)

p = GPIO.PWM(pin,50)
i=2.5
p.start(i)
server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)

port = server_sock.getsockname()[1]

uuid = "00000000-0000-1000-8000-00805F9B34FB"
advertise_service( server_sock, "SampleServer",service_id = uuid, service_classes = [ uuid, SERIAL_PORT_CLASS ], profiles = [ SERIAL_PORT_PROFILE ], )
#                   protocols = [ OBEX_UUID ] 

print "Waiting for connection on RFCOMM channel %d" % port

client_sock, client_info = server_sock.accept()
print "Accepted connection from ", client_info

try:
	while True:
		data = client_sock.recv(1024)
		if len(data) == 0: break
		print "received [%s]" % data
		while i < 10.5:
			GPIO.output(TRIG, False)
			time.sleep(0.0002)
			GPIO.output(TRIG, True)
			time.sleep(0.00001)
			GPIO.output(TRIG, False)

			while GPIO.input(ECHO)==0:
				pulse_start = time.time()

			while GPIO.input(ECHO)==1:
				pulse_end = time.time()

			pulse_duration = pulse_end - pulse_start
			distance = pulse_duration * 17150

			distance = round(distance, 2)
			print "Distance:",distance,"cm"
			client_sock.send(distance)
			p.ChangeDutyCycle(i)
			time.sleep(0.1)
			i += 2
		while i > 2.5:
			GPIO.output(TRIG, False)
			time.sleep(0.0002)
			GPIO.output(TRIG, True)
			time.sleep(0.00001)
			GPIO.output(TRIG, False)

			while GPIO.input(ECHO)==0:
				pulse_start = time.time()

			while GPIO.input(ECHO)==1:
				pulse_end = time.time()

			pulse_duration = pulse_end - pulse_start
			distance = pulse_duration * 17150

			distance = round(distance, 2)
			print "Distance:",distance,"cm"
			client_sock.send(distance)
			p.ChangeDutyCycle(i)
			time.sleep(0.1)
			i -= 2


except IOError:
	pass
	print "disconnected"
	client_sock.close()
	server_sock.close()

except KeyboardInterrupt:
	p.stop()
	GPIO.cleanup()