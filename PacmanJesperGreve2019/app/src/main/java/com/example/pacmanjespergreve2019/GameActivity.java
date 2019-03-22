package com.example.pacmanjespergreve2019;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private GameViewManager gameViewManager = null;
    private Handler redrawHandler = new Handler();
    private int displayWidth, displayHeight;

    //Gameloop variables:
    private Timer timer = null;
    private TimerTask timerTask;
    float deltaTime = 0f;
    public static boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(0xFFFFFFFF,
                WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //final RelativeLayout mainView = (RelativeLayout) findViewById((R.id.main));
        final RelativeLayout mainView = findViewById(R.id.main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        displayWidth = size.x;
        displayHeight = size.y;

        gameOver = false;

        gameViewManager = new GameViewManager(this, display);

        mainView.addView(gameViewManager);
        gameViewManager.invalidate();
    }

    @Override
    public void onResume() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                long startFrameTime = System.nanoTime();
                if (gameOver) {
                    EndGame();
                }


                gameViewManager.Update((float) deltaTime);

                redrawHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameViewManager.invalidate();
                    }
                });

                deltaTime = (System.nanoTime() - startFrameTime) / 10000000.f;

            }
        };
        timer.schedule(timerTask, 10, 10);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Restart");
        menu.add("Exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause()
    {
        timer.cancel();
        timer = null;
        timerTask = null;
        super.onPause();
    }

    public void EndGame()
    {
        Intent intent = new Intent(GameActivity.this,GameOver.class);
        SharedPreferences prefs = getSharedPreferences("PacMan_HighScore", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("Score",GameViewManager.score);
        editor.apply();
        startActivity(intent);

    }

    //listener for config change.
    //This is called when user tilts phone enough to trigger landscape view
    //we want our app to stay in portrait view, so bypass event
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }
}
