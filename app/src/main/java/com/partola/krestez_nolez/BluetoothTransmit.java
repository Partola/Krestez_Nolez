package com.partola.krestez_nolez;

import java.io.IOException;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

class BluetoothTransmit extends Thread {
	String receiveString;
	byte[] buff;
	private final OutputStream traStream;
	
	public BluetoothTransmit(BluetoothSocket socket){
		OutputStream tmpOut = null;
		try {
			tmpOut = socket.getOutputStream();
            Log.d("BluetoothTransmit", "поток записи создан");
        } catch (IOException e) {
        	Log.e("BluetoothTransmit", "ошибка создания потока записи");
        }
		traStream = tmpOut;
	}
	
	public void run() {
		write(buff);
	}

	public void setBuff(byte[] bytes) {
		buff = bytes;
    }
	
	public void write(byte[] bytes) {
        try {
        	traStream.write(bytes);
        	Log.d("BluetoothTransmit", "запись завершена");
        } catch (IOException e) {
        	Log.e("BluetoothTransmit", "Ошибка отправки сообщения");
        }
    }
}
