# this code will be run in raspberry pi

import serial
from socketIO_client_nexus import SocketIO, BaseNamespace, LoggingNamespace

ser = serial.Serial(
    port='/dev/ttyACM0',
    baudrate='9600',
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
    timeout=1
)


def on_connect():
    print('Connected!')


def on_reconnect():
    print('Reconnected.')


def on_disconnect():
    print('Disconnected..')


def on_send_rasp(*args):
    print(args[0]['send'])
    letter = args[0]['send']
    ser.write(letter.encode('ascii'))


with SocketIO('localhost', 8801) as socketIO:
    socketIO.on('sendRasp', on_send_rasp)
    try:
        while 1:
            response = ser.readline()
            realResponse = response.decode('utf-8')[:len(response) - 1]
            print(realResponse)
            socketIO.emit('rasp', {'data': realResponse})
            socketIO.wait(seconds=1)
    except KeyboardInterrupt:
        ser.close()
