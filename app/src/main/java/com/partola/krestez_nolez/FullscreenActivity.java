package com.partola.krestez_nolez;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.partola.krestez_nolez.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity implements OnClickListener{

    Button btnStart;
    ImageButton btnStartIm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);

        btnStartIm = (ImageButton) findViewById(R.id.imageButton);
        btnStart = (Button) findViewById(R.id.button);
        btnStartIm.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        Bitmap btmp;
        Resources res = this.getResources();
        btmp = BitmapFactory.decodeResource(res, R.drawable.start);
        btnStartIm.setImageBitmap(btmp);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    public void onClick(View v){
        switch(v.getId()){
            case R.id.button:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
