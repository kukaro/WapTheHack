# this code will be run in raspberry pi

import serial
from socketIO_client_nexus import SocketIO, BaseNamespace, LoggingNamespace

ser = serial.Serial(
    port='/dev/cu.usbmodem1411',
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


def turn_off_gas(*args):
    print(args[0]['send'])
    letter = args[0]['send']
    ser.write(letter.encode('ascii'))


with SocketIO('192.168.1.103', 8801) as socketIO:
    socketIO.on('gasOff', turn_off_gas)
    try:
        while 1:
            response = ser.readline()
            realResponse = response.decode('utf-8')[:len(response) - 1]
            print(realResponse)
            dataArr = realResponse.split(" ")
            inWater = dataArr[0][3:]
            outWater = dataArr[1][3:]
            gas = dataArr[2][3:]
            print(inWater, outWater, gas)
            socketIO.emit('rasp', {'inWater': inWater, 'outWater': outWater, 'gas': gas})
            socketIO.wait(seconds=1)
    except KeyboardInterrupt:
        ser.close()
