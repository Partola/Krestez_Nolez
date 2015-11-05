package com.partola.krestez_nolez;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.partola.krestez_nolez.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity implements OnClickListener{

    Button btnStart, btnPlayBt;
    ListView lv;
    int resCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);

        btnStart = (Button) findViewById(R.id.button);
        btnStart.setOnClickListener(this);

        btnPlayBt = (Button) findViewById(R.id.button2);
        btnPlayBt.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                Intent intent = new Intent(this, GameActivityNB.class);
                startActivity(intent);
                break;
            case R.id.button2:
                Intent intent1 = new Intent(this, SetBTActivity.class);
                startActivityForResult(intent1, resCode);
                break;
            default:
                break;
        }
    }
    public void onDestroy(){
        super.onDestroy();
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        if (bta.isEnabled()){
            bta.disable();
        }
        BluetoothConnect btc = BluetoothConnect.getInstance();
        if(btc != null){
            btc.cancel();
        }
    }
}
