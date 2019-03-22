package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import java.util.List;
import java.util.Vector;

public class GameViewManager extends View
{
    private Context context;
    private Display display;

    public static boolean debugging = true;
    public static int score = 0;
    public static int lives = 3;
    public static List<GameObject> objects;
    public long lastStartTime;

    public Player player;
    float dt = 0;

    public GameViewManager(Context context, Display display)
    {
        super(context);
        this.context = context;
        this.display = display;
        GameSetup();
    }

    private void GameSetup()
    {
        this.score = 0;
        this.lives = 3;
        this.lastStartTime = 0;
        objects = new Vector<GameObject>();

        //Hardcoded player spawn
        player = new Player(R.drawable.playershipwhite,200,200,66f,113f,this.context);
        objects.add(player);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //Draw all objects:
        for(GameObject go: objects)
        {
            go.Draw(canvas);
        }

        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/digital-7.ttf");
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setTextSize(55);
        //p.setTypeface(tf);
        canvas.drawText("SCORE: " + score + "", 100, 70, p);

        canvas.drawText("Pos: " + player.position.x + "," + player.position.y, 100, 120, p);

        canvas.drawText("dt: " + dt + " fps" + (int)(1/dt), 100, 170, p);

    }

    public void Update(float deltaTime)
    {
        dt = deltaTime;
        for(GameObject go: objects)
        {
            go.Update(deltaTime);
        }
    }


}
