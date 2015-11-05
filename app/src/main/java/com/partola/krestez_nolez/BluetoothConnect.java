package com.partola.krestez_nolez;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;


class BluetoothConnect extends Thread {
    private static BluetoothConnect instanseServ;
    private static BluetoothConnect instanseCli;
	private static BluetoothSocket mmSocket;
	private BluetoothServerSocket mmServerSocket;
    private final BluetoothDevice mmDevice;
	private boolean serv;
    private boolean socketCreated = false;
    private boolean socketCreationError = false;
    private boolean socketConnected = false;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    Handler hndlr;
    
    private BluetoothConnect(BluetoothDevice device, Handler hndlr) {
        mmDevice = device;
        mmSocket = null;
        mmServerSocket = null;
        this.hndlr = hndlr;
        }
    private BluetoothConnect(Handler hndlr, boolean serv) {
        mmDevice = null;
        mmSocket = null;
        mmServerSocket = null;
        this.hndlr = hndlr;
        this.serv = serv;
    }
 
    public void run() {
        if(!serv){
    	try {
    		mmSocket = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
    		socketCreated = true;
    		socketCreationError = false;
    		hndlr.obtainMessage(R.string.socket_created, socketCreated).sendToTarget();
    		Log.d("BluetoothConnect", "Сокет создан: " + mmSocket.getRemoteDevice());
        } catch (IOException e) {
    	   mmSocket = null;
    	   socketCreated = false;
    	   socketCreationError = true;
    	   hndlr.obtainMessage(R.string.socket_creation_err, socketCreated).sendToTarget();
    	   Log.e("BluetoothConnect", "Ошибка создания сокета: " + e.getLocalizedMessage());
        }
            if (mmSocket != null){
            try {
                mmSocket.connect();
                socketConnected = true;
                hndlr.obtainMessage(R.string.socket_connected, socketConnected).sendToTarget();
                Log.d("BluetoothConnect", "Сокет подключен!");
            } catch (IOException e) {
                Log.d("BluetoothConnect", "Сокет не подключен");
                Log.e("BluetoothConnect", "Сокет не подключен!" + e.getLocalizedMessage());
            }
            }
        }
        if(serv){
            BluetoothServerSocket tmp = null;
            try {
                tmp = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("BTTm server socket", MY_UUID);
                mmServerSocket = tmp;
                try {
                    mmSocket = mmServerSocket.accept();
                    hndlr.obtainMessage(R.string.server_socket_created, socketCreated).sendToTarget();
                    Log.d("BluetoothConnect", "Серверный сокет подключен");
                } catch (IOException ee) {
                    mmSocket = null;
                    socketConnected = false;
                    hndlr.obtainMessage(R.string.socket_connection_err, socketCreated).sendToTarget();
                    Log.e("BluetoothConnect", "Ошибка подключения серверного сокета: "+ee.getLocalizedMessage());
                }
                try {
                    mmServerSocket.close();
                } catch (IOException eee) {
                    Log.e("BluetoothConnect", "Ошибка закрытия серверного сокета: "+eee.getLocalizedMessage());
                }
            } catch (IOException ioe) {
                Log.e("BluetoothConnect", "Ошибка создания серверного сокета: " + ioe.getLocalizedMessage());
            }
        }
        return;
    }
    
    public static BluetoothSocket getSocket() {
        return mmSocket;
    }
    public void cancel() {
    	if (mmSocket != null){
	    	try{
	            mmSocket.close();
	            mmSocket = null;
	        }catch(IOException ioe){
				Log.e("BluetoothConnect", "Ошибка закрытия сокета при отмене : " + ioe.getLocalizedMessage());
			}
    	}
    	if (mmServerSocket != null){
	        try {
	            mmServerSocket.close();
				mmServerSocket = null;
	        } catch (IOException e) {
				Log.e("BluetoothConnect", "Ошибка закрытия серверного сокета при отмене : " + e.getLocalizedMessage());
			}
        }
    }
    public boolean socketIsCreated() {
        return socketCreated;
    }
    public boolean socketIsCreationError() {
        return socketCreationError;
    }
	public boolean socketIsConnected() {
	    return socketConnected;
	}
    public static synchronized BluetoothConnect getInstanceServ(Handler hndlr, boolean serv){
        if(instanseServ == null){
            return new BluetoothConnect(hndlr, serv);
        }
        return instanseServ;
    }
    public static synchronized BluetoothConnect getInstanceCli(BluetoothDevice device, Handler hndlr){
        if(instanseCli == null){
            return new BluetoothConnect(device, hndlr);
        }
        return instanseCli;
    }
    public static synchronized BluetoothConnect getInstance(){
        if(instanseServ == null){
            if(instanseCli == null){
                return null;
            }else{
                return instanseCli;
            }
        }else{
            return instanseServ;
        }
    }

}