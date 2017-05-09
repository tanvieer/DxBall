package afinal.dxball.tanvir.com.dxball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class SecondLevel extends AppCompatActivity {

    View secondLevel;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("initialmsg","onCreate Second");

        GameResultController.newLife();
        GameResultController.score = 0;
        ResetSharedPref(this);

        secondLevel = new SecondLevelGamePanel(this);

        setContentView(secondLevel);

        secondLevel.setBackgroundColor(Color.BLACK);

        Log.d("initialmsg","onCreate FirstLevel set background color white");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // MyService.player.stop();

        GameResultController.newLife();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        //bouncingBallView.setVisibility(View.GONE);
        Log.d("initialmsg","onBackPressed SecondLevel before destroyDrawingCache");
        secondLevel.destroyDrawingCache();
        Log.d("initialmsg","onBackPressed SecondLevel after destroyDrawingCache");
    }



    private void ResetSharedPref(Context context){
        GameResultController.score = 0;

        SharedPreferences.Editor editor = context.getSharedPreferences("My prefs", context.MODE_PRIVATE).edit();
        editor.putString("current score", "" + GameResultController.score);

        editor.commit();
    }


}
