package com.example.pacmanjespergreve2019;

import android.app.Activity;
import android.content.Context;
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

public class GameActivity extends Activity
{
    private GameViewManager gameViewManager = null;
    private Handler redrawHandler = new Handler();
    private int displayWidth, displayHeight;

    //Gameloop variables:
    private Timer timer = null;
    private TimerTask timerTask;
    float deltaTime = 0f;
    public static boolean gameOver = false;
    public static boolean touchControls;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Request no titlebar, also disabled notification pull down.
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
        touchControls = getSharedPreferences("FishGameJG", Context.MODE_PRIVATE).getBoolean("control",false);

        gameViewManager = new GameViewManager(this, display);

        mainView.addView(gameViewManager);
        gameViewManager.invalidate();
    }

    public void EndGame()
    {
        Intent intent = new Intent(GameActivity.this, GameOver.class);
        SharedPreferences prefs = getSharedPreferences("PacMan_HighScore", MODE_PRIVATE);
        //final SharedPreferences.Editor editor = prefs.edit();

        //editor.putInt("Score", (int)GameViewManager.score);
        //editor.apply();
        intent.putExtra("Score", (int)GameViewManager.score);
        startActivity(intent);
    }

    //Ended up not using this:
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Restart");
        menu.add("Exit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
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

    @Override
    public void onResume()
    {
        timer = new Timer();
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                long startFrameTime = System.nanoTime();
                if (gameOver)
                {
                    EndGame();
                }

                gameViewManager.Update(deltaTime);

                redrawHandler.post(new Runnable()
                {
                    public void run()
                    {
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
    public void onDestroy()
    {
        super.onDestroy();
        System.runFinalization();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    //listener for config change.
    //This is called when user tilts phone enough to trigger landscape view
    //we want our app to stay in portrait view, so bypass event
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed()
    {
        Intent mainMenu = new Intent(GameActivity.this, MainMenu.class);
        GameActivity.this.startActivity(mainMenu);
    }
}
