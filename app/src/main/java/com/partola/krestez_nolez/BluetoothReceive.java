package com.partola.krestez_nolez;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

class BluetoothReceive extends Thread {
	
	final int SEND_HEX = 0;
	final int SEND_HEX_CRC = 1;
	final int SEND_TEXT = 2;
	final int SEND_TEXT_CS = 3;
	final int SEND_AT = 4;
	
	private String receiveString;
    private byte knn[] = new byte[4];
	private final InputStream reStream;
	int receive_format = 1;
	
	public BluetoothReceive(BluetoothSocket socket){
		InputStream tmpIn = null;
		try {
			tmpIn = socket.getInputStream();
            Log.d("BluetoothReceive", "поток чтения создан");
        } catch (IOException e) {
        	Log.e("BluetoothReceive", "ошибка создания потока чтения");
        } catch (NullPointerException npe){
            Log.e("BluetoothReceive", "NULL ошибка создания потока чтения");
        }
		reStream = tmpIn;
	}
	
	public void run() {
		while (true) {
			byte[] buff = new byte[1024];
			int bufferLength = 0;
            try {
                Log.d("BluetoothReceive", "Чтение сейчас начнется");
            	while((bufferLength = reStream.read(buff)) < 65535){
                    Log.d("BluetoothReceive", "Вычитаны данные");
                    knn[0] = buff[0];
                    knn[1] = buff[1];
                    knn[2] = buff[2];
                    knn[3] = buff[3];
	            	GameActivity.handReceiveString.obtainMessage(1, buff).sendToTarget();
                    Arrays.fill(buff, (byte)0);
            	}
            } catch (IOException e) {
            	Log.e("BluetoothReceive", "Ошибка потока чтения: " + e.getLocalizedMessage());
                break;
            }
        }		
	}
	public String getString(){
		return receiveString;
	}
    public byte[] getKnn(){
        return knn;
    }
	public void clearString(){
		receiveString = "";
	}
    public void clearStream(){
        try{
            reStream.close();
            Log.d("BluetoothReceive", "Поток чтения зарыт");
        }catch(Exception e){
            Log.e("BluetoothReceive", "Поток чтения не закрыт!" + e.getLocalizedMessage());
        }
    }
}
