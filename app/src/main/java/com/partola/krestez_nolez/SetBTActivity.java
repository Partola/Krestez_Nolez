package com.partola.krestez_nolez;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.Set;

public class SetBTActivity extends Activity {

    ArrayAdapter<String> mArrayAdapter;
    BluetoothAdapter bta;
    ListView lv;
    TextView tv;
    Button btnConServ;
    BroadcastReceiver mReceiver = null;
    public static BluetoothConnect bcnct;
    public static Handler handSocketState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_bt);
        final Context cont = this;
        handSocketState = new Handler(){
            public void handleMessage(android.os.Message msg) {
                Toast.makeText(getApplicationContext(), msg.what, Toast.LENGTH_LONG).show();
                if((msg.what == R.string.server_socket_created) || (msg.what == R.string.socket_connected)){
                    Intent intent2 = new Intent(cont, GameActivity.class);
                    startActivity(intent2);
                    SetBTActivity.this.finish();
                }
            };
        };

        mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lv = (ListView)findViewById(R.id.listView);
        tv = (TextView)findViewById(R.id.textView);
        btnConServ = (Button)findViewById(R.id.button3);
        btnConServ.setClickable(false);
        btnConServ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BluetoothConnect.getInstanceServ(handSocketState, true).start();
            }
        });
        if(mReceiver == null){
            initBroadcastReceiver();
        }
        bta = BluetoothAdapter.getDefaultAdapter();
        if (!bta.isEnabled()){
            bta.enable();
            tv.setText(getResources().getString(R.string.waiting_BT));
        }else{
            tv.setText(getResources().getString(R.string.bounded_Devices));
            Toast.makeText(getBaseContext(), R.string.bluetooth_enable, Toast.LENGTH_SHORT).show();
            btnConServ.setClickable(true);
            viewBoundedDevice();
        }
        lv.setAdapter(mArrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                String str = mArrayAdapter.getItem(position).substring(mArrayAdapter.getItem(position).indexOf("\n") + 1);
                BluetoothConnect.getInstanceCli(bta.getRemoteDevice(str), handSocketState).start();
            }
        });
    }

    public void onResume(){
        super.onResume();
        if (!bta.isEnabled()){
            bta.enable();
            tv.setText(getResources().getString(R.string.waiting_BT));
        }else{
            tv.setText(getResources().getString(R.string.bounded_Devices));
            Toast.makeText(getBaseContext(), R.string.bluetooth_enable, Toast.LENGTH_SHORT).show();
            btnConServ.setClickable(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_bt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void initBroadcastReceiver(){
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())){
                    if(bta.isEnabled()){
                        viewBoundedDevice();
                        tv.setText(getResources().getString(R.string.bounded_Devices));
                        btnConServ.setClickable(true);
                    }
                }
            }
        };
        // Регистрируем BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    protected void viewBoundedDevice() {
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    }
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null){
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }

    }
}
