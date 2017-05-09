package afinal.dxball.tanvir.com.dxball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Tanvir on 06-May-17.
 */
public class FirstLevel extends AppCompatActivity {


    View bouncingBallView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("initialmsg","onCreate FirstLevel");

        GameResultController.newLife();
        GameResultController.score = 0;
        ResetSharedPref(this);

        bouncingBallView = new BouncingBall(this);

        setContentView(bouncingBallView);

        bouncingBallView.setBackgroundColor(Color.WHITE);

        Log.d("initialmsg","onCreate FirstLevel set background color white");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // MyService.player.stop();

        GameResultController.newLife();
        GameResultController.score = 0;
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //bouncingBallView.setVisibility(View.GONE);
        Log.d("initialmsg","onBackPressed FirstLevel before destroyDrawingCache");
        bouncingBallView.destroyDrawingCache();
        Log.d("initialmsg","onBackPressed FirstLevel after destroyDrawingCache");
    }



    private void ResetSharedPref(Context context){
        GameResultController.score = 0;

        SharedPreferences.Editor editor = context.getSharedPreferences("My prefs", context.MODE_PRIVATE).edit();
        editor.putString("current score", "" + GameResultController.score);

        editor.commit();
    }

}
