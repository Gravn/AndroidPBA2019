package com.example.pacmanjespergreve2019;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighscoreActivity extends Activity
{
    LinearLayout verticalLayout;
    DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        verticalLayout = (LinearLayout)findViewById(R.id.VerticalLayout);
        dbManager = new DbManager(this,"FishGameHighScore");

        for(int i=0;i<100;i++)
        {
            String[] dbresult = dbManager.GetScoreByIndex(i);
            if(dbresult != null)
            {
                AddScoreBar(dbresult[0], dbresult[1]);
            }
        }
    }

    private void AddScoreBar(String name, String score)
    {
        //Generate list children:

        LinearLayout horizontalLayout = new LinearLayout(this);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams
                (
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        10f
                );

        TextView nameText = new TextView(this);
        TextView scoreText = new TextView(this);

        nameText.setLayoutParams(textParams);
        textParams.weight = 5f;
        scoreText.setLayoutParams(textParams);

        nameText.setText(name);
        scoreText.setText(score);

        horizontalLayout.addView(nameText);
        horizontalLayout.addView(scoreText);

        verticalLayout.addView(horizontalLayout);
    }

    public void MainMenu(View v)
    {
        Intent mainMenu = new Intent(HighscoreActivity.this, MainMenu.class);
        HighscoreActivity.this.startActivity(mainMenu);
    }
}
