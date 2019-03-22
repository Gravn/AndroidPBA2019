package com.example.pacmanjespergreve2019;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity
{
    private Toast clickConfirmer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_menu);
    }

    public void StartGame(View v)
    {
        clickConfirmer = Toast.makeText(getApplicationContext(),"Starting Game",Toast.LENGTH_LONG);
        clickConfirmer.setGravity(Gravity.BOTTOM,0,160);
        Intent StartGame = new Intent(MainMenu.this,GameActivity.class);
        MainMenu.this.startActivity(StartGame); startActivity(StartGame);
    }

    public void ViewHighscore(View v)
    {

    }

    public void ViewSettings()
    {

    }

    public void ExitApplication()
    {

    }
    /*
    //Click/menu button switch
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Main_btn_Play:
                StartGame();
                break;

            case R.id.Main_btn_Highscore:
                ViewHighscore();
                break;
        }
    }
    */
}
