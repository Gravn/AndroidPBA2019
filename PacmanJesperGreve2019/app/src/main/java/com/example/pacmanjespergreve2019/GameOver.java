package com.example.pacmanjespergreve2019;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOver extends Activity
{
    DbManager dbMan;
    private SharedPreferences sharedPreferences;
    int score;

    private EditText playerName;
    private TextView scoreLabel;
    private Button submit;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        score = getIntent().getIntExtra("Score", 0);
        sharedPreferences = getSharedPreferences("FishGameJG", Context.MODE_PRIVATE);

        playerName = (EditText)findViewById(R.id.playerName);
        playerName.setText(sharedPreferences.getString("Name", "Name"));

        scoreLabel = (TextView)findViewById(R.id.score);
        scoreLabel.setText("Score: "+score);

        dbMan = new DbManager(this,"FishGameHighScore");
    }

    public void SubmitButton(View view)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String name = playerName.getText().toString();
        if (name.length() > 0)
        {
            editor.putString("Name", name);
            editor.apply();
            finish();
            dbMan.Insert(name, score);


            Intent intent = new Intent(GameOver.this,HighscoreActivity.class);
            startActivity(intent);
        }
        else
        {
            Log.d("G","name too short");
            //Snackbar: Please enter a name.
            ShowSnackBar(view,"Please enter a name", Snackbar.LENGTH_LONG);
        }
    }

    private void ShowSnackBar(View view, String message,int duration)
    {
        Snackbar snackbar = Snackbar.make(view, message,duration);
        snackbar.getView().findViewById(android.support.design.R.id.snackbar_text).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add("Restart");
        menu.add("Exit");
        menu.add("Game");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
