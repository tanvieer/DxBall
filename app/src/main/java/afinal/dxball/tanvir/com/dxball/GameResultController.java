package afinal.dxball.tanvir.com.dxball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by Tanvir on 06-May-17.
 */

public class GameResultController {

    public static int score = 0;
    public static int life = 3;
    private Paint paint;


    public GameResultController(int color) {
        paint = new Paint();
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(16);
        paint.setColor(color);
    }

    public static void lifeLost(){
        life--;
    }

    public static void newLife(){
        life = 3;
    }

    public static int getLife(){
        return life;
    }


    public void gameOver(Canvas canvas){
        paint.setTextSize(64f);
        canvas.drawText("GAME OVER",200,550,paint);
    }

    public void drawLifeScore(Canvas canvas) {
        canvas.drawText("SCORE : "+score, 10, 30, paint);
        canvas.drawText("LIFE : "+life,canvas.getWidth()-100,30,paint);
    }

    public void newLevel(Canvas canvas){
        paint.setTextSize(48f);
        paint.setColor(Color.BLUE);
        canvas.drawText("Loading New Level",120,550,paint);
    }
}
