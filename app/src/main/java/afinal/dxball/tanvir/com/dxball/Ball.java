package afinal.dxball.tanvir.com.dxball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Tanvir on 06-May-17.
 */

public class Ball {
    float radius = 20;
    float x = radius +20;
    float y = radius + 300;

    public float speedX = 5;
    public float speedY = 3;

    private RectF bounds;   //canvas.drawOval
    private Paint paint;

    private MediaPlayer player;

    Context context;
    Handler handler;


    public Ball(int color, Context context){
        this.bounds = new RectF();
        this.paint = new Paint();
        this.paint.setColor(color);
        this.context = context;
        this.handler = new Handler();
    }
    public RectF getBounds(){
        return bounds;
    }

    public void colSound(){
        player = MediaPlayer.create(context, R.raw.ball);
        player.start();
    }

    public void brickDestroySound(){
        player = MediaPlayer.create(context, R.raw.brickdestroy);
        player.setVolume(1.0f, 1.0f);
        player.start();
    }

    public void draw(Canvas canvas) {

        handler.post(new Runnable() {

            public void run() {
                bounds.set(x-radius, y-radius, x+radius, y+radius);

            }
        });
        canvas.drawOval(bounds, paint);

    }



    public void collideWithBrick(Brick brick,int left,int top,int right,int bottom) {


        if(x+10 == left){
            speedX = - speedX;
        }else if(x-10 == right){
            speedX = -speedX;
        }else {
            speedY = -speedY;
        }


        x += speedX;
        y += speedY;

        brick.set(0,0,0,0);

        brickDestroySound();

    }


    public void barCol(Brick brick,int left,int right){


        if(x+10 == left){
            speedX = - speedX;
        }else if(x-10 == right){
            speedX = -speedX;
        }else {
            speedY = -speedY;
        }

        x += speedX;
        y += speedY;

        y -= radius;
        colSound();
        Log.e("ball1",""+(x-radius)+"    "+(y-radius)+"  "+x+radius+"    "+y+radius);
    }


    public void moveWithCollisionDetection(int xMax, int yMax) {
        // Get new (x,y) position
        int xMin = 0,yMin=0;
        x += speedX;
        y += speedY;
        // Detect collision and react
        if (x + radius > xMax) {   // right col
            speedX = -speedX;
            x = xMax-radius;

            colSound();
        } else if (x - radius < xMin) {   // left col
            speedX = -speedX;
            x = radius;
            colSound();
        }
        if (y + radius > yMax) {    // down col
            speedY = -speedY;
            y = yMax - radius;
            Toast.makeText(context,"1 LIFE LOST !!!", Toast.LENGTH_SHORT).show();

            GameResultController.lifeLost();
            colSound();
        } else if (y - radius < yMin) {    // up col
            speedY = -speedY;
            y = yMin + radius;
            colSound();
        }

    }


}
