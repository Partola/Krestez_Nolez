package com.partola.krestez_nolez;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.partola.krestez_nolez.util.SystemUiHider;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity implements OnClickListener{

    private Button btnStart, btnPlayBt;
    private CheckBox checkBoxMute;
    private MediaPlayer mediaPlayer;
    private boolean musicIsReady;
    private int resCode;

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

        checkBoxMute = (CheckBox) findViewById(R.id.checkBox);
        checkBoxMute.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                musicIsReady = true;
            }
        });
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
            case R.id.checkBox:
                if (!musicIsReady) {
                    break;
                }
                if (checkBoxMute.isChecked()) {
                    mediaPlayer.start();
                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                }
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
