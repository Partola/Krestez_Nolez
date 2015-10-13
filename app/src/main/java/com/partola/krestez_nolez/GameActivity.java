package com.partola.krestez_nolez;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends Activity implements View.OnTouchListener {

    int kn[][] = new int[3][3];
    boolean move;
    boolean gameOver = false;
    DrawView dw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dw = new DrawView(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(dw);
        //setContentView(new DrawView(this));
        //setContentView(R.layout.activity_game);
        dw.setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_UP){
            int x = (int)event.getRawX();
            int y = (int)event.getRawY();
            int sizeX = dw.getWidth();
            int sizeY = dw.getHeight();
            int deltaX, deltaY, tmp1, tmp2;

            if (gameOver){
                gameOver = false;
                kn = null;
                kn = new int[3][3];
                move = false;
                dw.invalidate();
                return false;
            }
            if (y < (sizeY / 2 - sizeX / 6)){
                tmp2 = 0;
            }else{
                if (y < (sizeY / 2 + sizeX / 6)){
                    tmp2 = 1;
                }else{
                    tmp2 = 2;
                }
            }
            deltaX = sizeX / 3;
            deltaY = sizeY / 3;

            tmp1 = x/deltaX;
            switch(tmp1){
                case 0:
                    switch(tmp2){
                        case 0:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        case 1:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        case 2:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:

                    switch(tmp2){
                        case 0:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        case 1:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        case 2:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    switch(tmp2){
                        case 0:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        case 1:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        case 2:
                            if(kn[tmp1][tmp2] == 0){
                                if (!move){
                                    kn[tmp1][tmp2] = 1;
                                    move = !move;
                                }
                                else{
                                    kn[tmp1][tmp2] = 2;
                                    move = !move;
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            dw.invalidate();
        }
        return true;
    }

    private int checkWin(){
        int k = 1;
        int win = -1;

        for (int i = 0; i < 3; i++){
            int knx = kn[i][0] * kn[i][1] * kn[i][2];
            int kns = kn[i][0] + kn[i][1] + kn[i][2];
            if((knx != 0) && ((kns % 3) == 0)){
                win = i;
            }
            knx = kn[0][i] * kn[1][i] * kn[2][i];
            kns = kn[0][i] + kn[1][i] + kn[2][i];
            if((knx != 0) && ((kns % 3) == 0)){
                win = i + 3 ;
            }
        }
        if(((kn[0][0] * kn[1][1] * kn[2][2]) != 0 )&&
        (((kn[0][0] + kn[1][1] + kn[2][2])%3) == 0)){
            win = 6;
        }
        if(((kn[0][2] * kn[1][1] * kn[2][0]) != 0 )&&
        (((kn[0][2] + kn[1][1] + kn[2][0])%3) == 0)){
            win = 7;
        }
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                k *= kn[i][j];
            }
        }
        if ((k != 0)&&(win == -1)){
            win = 8;
        }
        return win;
    }

    class DrawView extends View {

        private Bitmap mBitmap;

        public DrawView(Context context) {
            super(context);
            Resources res = this.getResources();
            mBitmap = BitmapFactory.decodeResource(res, R.drawable.cross);
        }

        private Paint mPaint = new Paint();

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.GREEN);
            int size, centrX, centrY;
            if (canvas.getWidth() < canvas.getHeight()){
                size = canvas.getWidth();
                centrX = canvas.getWidth() / 2;
                centrY = canvas.getHeight() / 2;
            }else{
                size = canvas.getHeight();
                centrX = canvas.getWidth() / 2;
                centrY = canvas.getHeight() / 2;
            }
            canvas.drawRect(centrX + size / 2 - size / 3, centrY - size / 2 + size / 30, centrX + size / 2 - size / 3 + 10, centrY + size / 2 - size / 30, mPaint);
            canvas.drawRect(centrX - size / 2 + size / 3, centrY - size / 2 + size / 30, centrX - size / 2 + size / 3 + 10, centrY + size / 2 - size / 30, mPaint);
            canvas.drawRect(centrX - size / 2 + size / 30, centrY - size / 2 + size / 3, centrX + size / 2 - size / 30, centrY - size / 2 + size / 3 + 10, mPaint);
            canvas.drawRect(centrX - size / 2 + size / 30, centrY + size / 2 - size / 3, centrX + size / 2 - size / 30, centrY + size / 2 - size / 3 + 10, mPaint);

            switch(kn[0][0]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX - size / 2 + 5, centrY - size / 2 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX - size / 2 + 5, centrY - size / 2 + 5);
                    break;
            }
            switch(kn[1][0]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX - size / 2 + size / 3 + 5, centrY - size / 2 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX - size / 2 + size / 3 + 5, centrY - size / 2 + 5);
                    break;
            }
            switch(kn[2][0]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX + size / 2 - size / 3 + 5, centrY - size / 2 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX + size / 2 - size / 3 + 5, centrY - size / 2 + 5);
                    break;
            }
            switch(kn[0][1]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX - size / 2 + 5, centrY - size / 2 + size / 3 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX - size / 2 + 5, centrY - size / 2 + size / 3 + 5);
                    break;
            }
            switch(kn[1][1]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX - size / 2 + size / 3 + 5, centrY - size / 2 + size / 3 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX - size / 2 + size / 3 + 5, centrY - size / 2 + size / 3 + 5);
                    break;
            }
            switch(kn[2][1]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX + size / 2 - size / 3 + 5, centrY - size / 2 + size / 3 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX + size / 2 - size / 3 + 5, centrY - size / 2 + size / 3 + 5);
                    break;
            }
            switch(kn[0][2]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX - size / 2 + 5, centrY + size / 2 - size / 3 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX - size / 2 + 5, centrY + size / 2 - size / 3 + 5);
                    break;
            }
            switch(kn[1][2]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX - size / 2 + size / 3 + 5, centrY + size / 2 - size / 3 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX - size / 2 + size / 3 + 5, centrY + size / 2 - size / 3 + 5);
                    break;
            }
            switch(kn[2][2]){
                case 0:
                    break;
                case 1:
                    drawCross(canvas, centrX + size / 2 - size / 3 + 5, centrY + size / 2 - size / 3 + 5);
                    break;
                case 2:
                    drawZero(canvas, centrX + size / 2 - size / 3 + 5, centrY + size / 2 - size / 3 + 5);
                    break;
            }
            int f = checkWin();
            if (f >= 0){
                switch(f){
                    case 0:
                        canvas.drawRect(centrX - size / 3, centrY - size / 2 - size / 4, centrX - size / 3 + 10, centrY + size / 2 + size / 4, mPaint);
                        break;
                    case 1:
                        canvas.drawRect(centrX, centrY - size / 2 - size / 4, centrX + 10, centrY + size / 2 + size / 4, mPaint);
                        break;
                    case 2:
                        canvas.drawRect(centrX + size / 3, centrY - size / 2 - size / 4, centrX  + size / 3 + 10, centrY + size / 2 + size / 4, mPaint);
                        break;
                    case 3:
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY - size / 3, centrX + size / 2 + size / 4, centrY - size / 3 + 10, mPaint);
                        break;
                    case 4:
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY, centrX + size / 2 + size / 4, centrY + 10, mPaint);
                        break;
                    case 5:
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY + size / 3, centrX + size / 2 + size / 4, centrY + size / 3 + 10, mPaint);
                        break;
                    case 6:
                        canvas.rotate(45, centrX, centrY);
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY, centrX + size / 2 + size / 4, centrY + 10, mPaint);
                        canvas.rotate(-45, centrX, centrY);
                        break;
                    case 7:
                        canvas.rotate(-45, centrX, centrY);
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY, centrX + size / 2 + size / 4, centrY + 10, mPaint);
                        canvas.rotate(45, centrX, centrY);
                        break;
                    case 8:
                        canvas.drawRect(centrX - size / 3, centrY - size / 2 - size / 4, centrX - size / 3 + 10, centrY + size / 2 + size / 4, mPaint);
                        canvas.drawRect(centrX, centrY - size / 2 - size / 4, centrX + 10, centrY + size / 2 + size / 4, mPaint);
                        canvas.drawRect(centrX + size / 3, centrY - size / 2 - size / 4, centrX  + size / 3 + 10, centrY + size / 2 + size / 4, mPaint);
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY - size / 3, centrX + size / 2 + size / 4, centrY - size / 3 + 10, mPaint);
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY, centrX + size / 2 + size / 4, centrY + 10, mPaint);
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY + size / 3, centrX + size / 2 + size / 4, centrY + size / 3 + 10, mPaint);
                        canvas.rotate(-45, centrX, centrY);
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY, centrX + size / 2 + size / 4, centrY + 10, mPaint);
                        canvas.rotate(45, centrX, centrY);
                        canvas.rotate(45, centrX, centrY);
                        canvas.drawRect(centrX - size / 2 - size / 4, centrY, centrX + size / 2 + size / 4, centrY + 10, mPaint);
                        canvas.rotate(-45, centrX, centrY);
                        break;
                    default:

                        break;
                }
                gameOver = true;
            }

        }

        private void drawCrossZero(Canvas canvas, int x, int y){
            if(move){
                drawZero(canvas, x, y);
            }else{
                drawCross(canvas, x, y);
            }
        }
        private void drawCross(Canvas canvas, int x, int y){
            int size, centrX, centrY;
            if (canvas.getWidth() < canvas.getHeight()){
                size = canvas.getWidth() / 3;
                centrX = size / 2 ;
                centrY = size / 2;
                size = (int)(size * Math.sqrt(2)) - 60;
            }else{
                size = canvas.getHeight() / 3;
                centrX = size / 2;
                centrY = size / 2;
                size = (int)(size * Math.sqrt(2)) - 60;
            }
            canvas.rotate(-45, x + centrX, y + centrY);
            canvas.drawRect(x + centrX - 5, y + centrY - size / 2, x + centrX + 5, y + centrY + size / 2, mPaint);
            canvas.rotate(+45, x + centrX, y + centrY);

            canvas.rotate(45, x + centrX, y + centrY);
            canvas.drawRect(x + centrX - 5, y + centrY - size / 2, x + centrX + 5, y + centrY + size / 2, mPaint);
            canvas.rotate(-45, x + centrX, y + centrY);
        }

        private void drawZero(Canvas canvas, int x, int y){
            int size, centrX, centrY;
            if (canvas.getWidth() < canvas.getHeight()){
                size = canvas.getWidth() / 3;
                centrX = size / 2 ;
                centrY = size / 2;
                size = (int)(size * Math.sqrt(2)) - 60;
            }else{
                size = canvas.getHeight() / 3;
                centrX = size / 2;
                centrY = size / 2;
                size = (int)(size * Math.sqrt(2)) - 60;
            }
            //mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(x + centrX, y + centrY, centrX - 10, mPaint);
            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(x + centrX, y + centrY, centrX - 20, mPaint);
            mPaint.setColor(Color.BLACK);
        }
    }
}
