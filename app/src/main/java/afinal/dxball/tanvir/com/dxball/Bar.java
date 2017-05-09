package afinal.dxball.tanvir.com.dxball;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Tanvir on 06-May-17.
 */

public class Bar {
    public int xMin, xMax, yMin, yMax;
    private Paint paint;
    private RectF bounds;

    public Bar(int color){
        this.paint = new Paint();
        this.paint.setColor(color);
        this.bounds = new RectF();
    }

    public void set(int x, int y, int width, int height){
        xMin = x;
        xMax = x + width - 1;
        yMin = y;
        yMax = y + height - 1;
        bounds.set(xMin, yMin, xMax, yMax);
    }

    public int getLeft(){
        return xMin;
    }

    public int getTop(){
        return yMin;
    }

    public int getRight(){
        return yMax;
    }

    public int getBottom(){
        return yMax;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(bounds, paint);
    }

    public RectF getBounds(){
        return bounds;
    }



}
