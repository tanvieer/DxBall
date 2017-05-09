package afinal.dxball.tanvir.com.dxball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tanvir on 06-May-17.
 */

public class BouncingBall extends View {
    private Ball ball;
    private Brick box;
    private ArrayList<Brick> listOfBrick;
    private Bar bar;
    private Brick brick;
    private Brick tempBrick;


    private int x = 5;
    private int y = 50;
    private int width = 140;
    private int height = 50;

    Context context;
    boolean time = false;

    public static AccelerometerSensorManagement accelerometerSensorManagement;

    int screenMaxX;
    int screenMaxY;

    Timer timer;
    MediaPlayer player;

    GameResultController gameResultController;

    public BouncingBall(final Context context){
        super(context);

        Log.d("initialmsg","BouncingBall constructor called");
        this.context = context;

        playBackgroundMusic();
        listOfBrick = new ArrayList<Brick>();

        brick = new Brick(Color.BLUE);      //blue brick
        brick.set(200,400,300,50);

        bar = new Bar(Color.GREEN);         // green bar
        bar.set(230,600,300,50);



        for(int i = 0;i<15;i++){

            if(i < 5) {
                tempBrick  = new Brick(Color.RED);
                tempBrick.set(x, y, width, height);

                x = x + width + 5;
                listOfBrick.add(tempBrick);

            }

            if(i == 5){
                setX(5);
            }

            if(i >= 5 && i < 10){
                tempBrick = new Brick(Color.YELLOW);
                tempBrick.set(x, y+height+10, width, height);
                x = x + width + 5;
                listOfBrick.add(tempBrick);
            }

            if(i == 10){
                setX(5);
            }

            if(i >= 10 ){

                tempBrick = new Brick(Color.LTGRAY);
                tempBrick.set(x, y+height+height+20, width, height);
                x = x + width + 5;
                listOfBrick.add(tempBrick);


            }
        }


        box = new Brick(0xff00003f);                 // ARGB
        ball = new Ball(Color.CYAN,getContext());    // CYAN Ball
        gameResultController = new GameResultController(Color.RED);

        //  StatusMessage code

       accelerometerSensorManagement = new AccelerometerSensorManagement(context);
        accelerometerSensorManagement.register();     // register sensor



        //===============================================================================input for bar from sensor Async====================================================================
        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        if(bar.getLeft() >0 && bar.getLeft() <screenMaxX-300){
                            bar.set(bar.getLeft() + -1*(int)accelerometerSensorManagement.getX(), screenMaxY-51, 300, 50);
                        }

                        else if(bar.getLeft() <1 && accelerometerSensorManagement.getX() < 0){   // left exception

                            bar.set(bar.getLeft() + -1*(int)accelerometerSensorManagement.getX(), screenMaxY-51, 300, 50);
                        }

                        else if(bar.getLeft() >=screenMaxX-300 && accelerometerSensorManagement.getX() > 0){   // right exception

                            bar.set(bar.getLeft() + -1*(int)accelerometerSensorManagement.getX(), screenMaxY-51, 300, 50);
                        }

                    }
                });
            }
        };
        timer.schedule(doAsyncTask,0,3);




    }

    public void setX(int x){
        this.x = x ;
    }

    boolean firstTime = true;

    protected void onDraw(final Canvas canvas) {

        if (firstTime) {
            screenMaxX = canvas.getWidth();
            screenMaxY = canvas.getHeight();
            firstTime = false;
        }





        if (gameResultController.getLife() == 0) {

            gameResultController.gameOver(canvas);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    player.stop();
                    accelerometerSensorManagement.unRegister();

                    Intent i = new Intent(context, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                    gameResultController.newLife();


                    SharedPreferences prefs = context.getSharedPreferences("My prefs", context.MODE_PRIVATE);
                    String currentHighScore = prefs.getString("score", "0");

                    SharedPreferences.Editor editor = context.getSharedPreferences("My prefs", context.MODE_PRIVATE).edit();
                    editor.putString("current score", "" + gameResultController.score);

                    Log.e("current", "" + gameResultController.score);

                    if (Integer.parseInt(currentHighScore) < gameResultController.score) {


                        editor.putString("score", "" + gameResultController.score);

                    }

                    editor.commit();
                    timer.cancel();
                    System.exit(0);
                }
            }, 3000);

        } else if (listOfBrick.size() * 10 <= gameResultController.score) {
            player.stop();
            accelerometerSensorManagement.unRegister();


            gameResultController.newLevel(canvas);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 10 seconds



                    gameResultController.newLife();


                    SharedPreferences prefs = context.getSharedPreferences("My prefs", context.MODE_PRIVATE);
                    String currentHighScore = prefs.getString("score", "0");

                    SharedPreferences.Editor editor = context.getSharedPreferences("My prefs", context.MODE_PRIVATE).edit();
                    editor.putString("current score", "" + gameResultController.score);

                    Log.e("current", "" + gameResultController.score);

                    if (Integer.parseInt(currentHighScore) < gameResultController.score) {
                        editor.putString("score", "" + gameResultController.score);
                    }

                    editor.putString("life", "" + gameResultController.getLife());
                    editor.commit();


                    Intent i = new Intent(context, SecondLevel.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                    timer.cancel();
                    System.exit(0);

                }
            }, 3000);


        } else {

        box.draw(canvas);

        ball.draw(canvas);
        gameResultController.drawLifeScore(canvas);

        bar.draw(canvas);

        for (int i = 0; i < listOfBrick.size(); i++) {

            listOfBrick.get(i).draw(canvas);

        }

        // Update the position of the ball, including collision detection and reaction.
        ball.moveWithCollisionDetection(screenMaxX, screenMaxY);


        if (ball.getBounds().intersect(bar.getBounds())) {
            ball.barCol(brick, bar.getLeft(), bar.getRight());
        }

        for (int i = 0; i < listOfBrick.size(); i++) {


            if (listOfBrick.get(i).getBounds().intersect(ball.getBounds())) {
                ball.collideWithBrick(listOfBrick.get(i), listOfBrick.get(i).getLeft(), listOfBrick.get(i).getTop(), listOfBrick.get(i).getRight(), listOfBrick.get(i).getBottom());   // IF BALL COLLIED WITH BRICK
                //statusMsg.update(ball);
                gameResultController.score += 10;

            }
        }
        invalidate();
    }
    }























    //============================================BackGround Music Method============================
    public void playBackgroundMusic(){
        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask doAsyncTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {

                    public void run() {

                        if(player == null){
                            player = MediaPlayer.create(getContext(), R.raw.background);
                        }

                        if(!player.isPlaying()){
                            //player.setLooping(true); // Set looping
                            player.setVolume(1.0f, 1.0f);
                            player.start();
                        }


                    }
                });
            }
        };
        timer.schedule(doAsyncTask,0,95000);
    }



//=====================================================SENSOR Input Class=================================================
    class AccelerometerSensorManagement implements SensorEventListener {

        private SensorManager sensorManager;
        private Sensor accelerometerSensor;
        private  float x;
        private  float y;
        private  float z;


        public AccelerometerSensorManagement(Context context){
            sensorManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(accelerometerSensor.getType() == Sensor.TYPE_ACCELEROMETER){
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        float getX(){
            return x;
        }

        float getY(){
            return y;
        }

        float getZ(){
            return z;
        }

        void register(){

            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("sensorInfo","Sensor registerListener");
        }

        public void unRegister(){
            sensorManager.unregisterListener(this);
            Log.d("sensorInfo","Sensor unregisterListener");
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        player.stop();
        accelerometerSensorManagement.unRegister();
    }
}
