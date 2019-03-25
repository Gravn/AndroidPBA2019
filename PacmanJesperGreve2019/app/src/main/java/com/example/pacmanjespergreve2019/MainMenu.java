package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.prefs.Preferences;

public class MainMenu extends AppCompatActivity
{
    private Toast clickConfirmer;
    private Switch controlSwitch;
    private boolean tiltControls;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_menu);
        controlSwitch = (Switch)findViewById(R.id.controlSwitch);
        controlSwitch.setChecked(getSharedPreferences("FishGameJG", Context.MODE_PRIVATE).getBoolean("control",false));
    }

    public void StartGame(View v)
    {
        clickConfirmer = Toast.makeText(getApplicationContext(),"Starting Game",Toast.LENGTH_LONG);
        clickConfirmer.setGravity(Gravity.BOTTOM,0,160);
        Intent StartGame = new Intent(MainMenu.this,GameActivity.class);
        MainMenu.this.startActivity(StartGame);
        //startActivity(StartGame);
    }

    public void ViewHighscore(View v)
    {
        Intent highscore = new Intent(MainMenu.this, HighscoreActivity.class);
        MainMenu.this.startActivity(highscore);
    }

    public void ToogleControl(View v)
    {

        SharedPreferences sharedPreferences = getSharedPreferences("FishGameJG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("control", controlSwitch.isChecked());
        editor.apply();
        String message;
        if(controlSwitch.isChecked())
        {
            message = "Your fish will now go to the touched position";
        }
        else
        {
            message = "Tilt your phone to move your fish";
        }

        Snackbar snackbar = Snackbar.make(v,message,Snackbar.LENGTH_LONG);
        snackbar.getView().findViewById(android.support.design.R.id.snackbar_text).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
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
