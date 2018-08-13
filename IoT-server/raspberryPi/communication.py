# this code will be run in raspberry pi

import serial
from socketIO_client_nexus import SocketIO, BaseNamespace, LoggingNamespace

ser = serial.Serial(
    port='/dev/cu.usbmodem1421',
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
    print(args)
    if len(args) is not 0:
        print(args[0]['send'])
        letter = args[0]['send']
        ser.write(letter.encode('ascii'))


def turn_off_light(*args):
    print(args)
    if len(args) is not 0:
        print(args[0]['lightoff'])
        letter = args[0]['lightoff']
        ser.write(letter.encode('ascii'))


with SocketIO('www.theceres.net', 8801) as socketIO:
    socketIO.on('gasOff', turn_off_gas)
    socketIO.on('lightOff', turn_off_light)
    try:
        while 1:
            try:
                response = ser.readline()
                realResponse = response.decode('utf-8')[:len(response) - 1]
                print(realResponse)
                dataArr = realResponse.split(" ")
                if len(dataArr) < 3:pass
                inWater = dataArr[0][3:]
                outWater = dataArr[1][3:]
                gas = dataArr[2][3:]
                print(inWater, outWater, gas)
                socketIO.emit('rasp', {'inWater': inWater, 'outWater': outWater, 'gas': gas})
                socketIO.wait(seconds=1)
            except TypeError:
                pass
    except KeyboardInterrupt:
        ser.close()
