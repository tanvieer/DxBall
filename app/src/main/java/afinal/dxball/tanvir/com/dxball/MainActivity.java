package afinal.dxball.tanvir.com.dxball;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView _highScore;
    TextView _currentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _highScore = (TextView)findViewById(R.id.textViewHighScore);
        _currentScore = (TextView)findViewById(R.id.textViewCurrentScore);

        SharedPreferences prefs = getSharedPreferences("My prefs", MODE_PRIVATE);
        String highScore = prefs.getString("score", "0");
        String current = prefs.getString("current score","0");

        _currentScore.setText(_currentScore.getText()+current);
        _highScore.setText(_highScore.getText()+highScore);

        Log.d("initialmsg","onCreate MainActivity");

    }

    public void onClickPlay(View view){
        Intent intent = new Intent(MainActivity.this,FirstLevel.class);
        Log.d("initialmsg","FirstLevel called by intent from MainActivity");
        GameResultController.score = 0;
        startActivity(intent);
        Log.d("initialmsg","FirstLevel started by intent from MainActivity");
        finish();
    }

    @Override
    public void onBackPressed() {
        GameResultController.score = 0;
        GameResultController.getLife();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
