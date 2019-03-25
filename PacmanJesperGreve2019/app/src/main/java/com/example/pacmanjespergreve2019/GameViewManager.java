package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class GameViewManager<Sprite> extends View
{
    private Context context;
    private Display display;
    private Point displaySize = new Point();

    public static boolean debugging = false;
    public static float score = 0;
    public static int lives = 3;
    private Bitmap lives_alive, lives_dead;
    public static List<GameObject> objects;
    public long lastStartTime;

    public static int[] sandtops =
    {
        R.drawable.sand_top_01,
        R.drawable.sand_top_02,
        R.drawable.sand_top_03,
        R.drawable.sand_top_04,
        R.drawable.sand_top_05,
        R.drawable.sand_top_06
    };

    public static float worldSpeed = 5f;
    public int[][] tileGrid = new int[15][];

    public Player player;
    float dt = 0;

    public GameViewManager(Context context, Display display)
    {
        super(context);
        this.context = context;
        this.display = display;
        display.getSize(displaySize);
        GameSetup();
    }

    private void GameSetup()
    {
        this.score = 0;
        this.lives = 3;
        this.lastStartTime = 0;

        lives_alive = BitmapFactory.decodeResource(context.getResources(),R.drawable.fish_dead);
        lives_dead = BitmapFactory.decodeResource(context.getResources(),R.drawable.fish_medium);

        objects = new Vector<GameObject>();

        objects.add(new Decor(R.drawable.bg_01,1800,1080-220f,0,128f,128f,this.context));
        objects.add(new Decor(R.drawable.bg_02,300,1080-220f,0,128f,128f,this.context));
        objects.add(new Decor(R.drawable.bg_03,1000,1080-220f,0,128f,128f,this.context));

        objects.add(new Decor(R.drawable.plant_grass_01,64,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_grass_02,258,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_green_01,546,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_green_02,239,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_green_03,258,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_orange_01,130,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_orange_02,846,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_purple_01,1180,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_purple_02,980,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_purple_03,760,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.plant_purple_04,400,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.stone_01,1400,1080-240f,1,128f,128f,this.context));
        objects.add(new Decor(R.drawable.stone_02,200,1080-240f,1,128f,128f,this.context));

        objects.add(new Fish(R.drawable.fish_small,2100*1,1080-600,4,128f,128f,this.context,0));
        objects.add(new Fish(R.drawable.fish_small,2100*2,1080-600,4,128f,128f,this.context,0));
        objects.add(new Fish(R.drawable.fish_small,2100*3,1080-600,4,128f,128f,this.context,0));
        objects.add(new Fish(R.drawable.fish_small,2100*4,1080-600,4,128f,128f,this.context,0));
        objects.add(new Fish(R.drawable.fish_small,2100*5,1080-600,4,128f,128f,this.context,0));
        objects.add(new Fish(R.drawable.fish_small,2100*6,1080-600,4,128f,128f,this.context,0));
        objects.add(new Fish(R.drawable.fish_small,2100*7,1080-600,4,128f,128f,this.context,0));

        player = new Player(R.drawable.fish_medium,300,300,5,128f,128f,this.context);

        objects.add(player);

        objects.add(new Fish(R.drawable.fish_large,2400*1,1080-500,5,128f,128f,this.context,1));
        objects.add(new Fish(R.drawable.fish_large,2400*3,1080-500,5,128f,128f,this.context,1));
        objects.add(new Fish(R.drawable.fish_large,2400*5,1080-500,5,128f,128f,this.context,1));
        objects.add(new Fish(R.drawable.fish_large,2400*6,1080-500,5,128f,128f,this.context,1));
        objects.add(new Fish(R.drawable.fish_large,2400*7,1080-500,5,128f,128f,this.context,1));
        objects.add(new Fish(R.drawable.fish_large,2400*8,1080-500,5,128f,128f,this.context,1));

        for(int i= 0;i<16;i++)
        {
            objects.add(new WorldTile(GetRandomSandTop(),128f*i,1080-128,10,128f,128f,this.context,true));
        }
    }

    public void Update(float deltaTime)
    {
        dt = deltaTime;
        score+= 0.1f;
        for(GameObject go: objects)
        {
            go.Update(deltaTime);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawARGB(255,178,237,255);

        //Draw all objects:
        for(GameObject go: objects)
        {
            go.Draw(canvas);
        }

        Rect rect = new Rect(0,0,lives_alive.getWidth(),lives_alive.getHeight());
        for(int i =0;i<3;i++)
        {
            if(lives <= i)
            {
                canvas.drawBitmap(lives_alive, rect, new Rect(96 * i, 0, 96*i + 64, 64), null);
            }
            else
            {
                canvas.drawBitmap(lives_dead, rect, new Rect(96 * i, 0, 96*i + 64, 64), null);
            }
        }


        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/aressence.tff"); //obsolete method?
        //R.font.aressence
        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setTextSize(48);
        //p.setTypeface(tf);
        canvas.drawText("SCORE: " + (int)score + "", 10, 1000, p);

        if(lives < 0)
        {
            GameActivity.gameOver = true;
        }
        //canvas.drawText("Pos: " + player.position.x + "," + player.position.y, 100, 120, p);

        //canvas.drawText("dt: " + dt + " fps" + (int)(1/dt), 100, 170, p);
    }

    public static int GetRandomSandTop()
    {
        Random r = new Random();
        return sandtops[r.nextInt(sandtops.length-1)];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        player.SetTargetPosition(new PointF(event.getX(),event.getY()));
        return true;
    }

}
