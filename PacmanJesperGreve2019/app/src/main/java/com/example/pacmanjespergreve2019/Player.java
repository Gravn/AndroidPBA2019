package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;

public class Player extends GameObject
{
    public PointF speed;

    public Player(int spriteID, float xPos, float yPos, float width,float height, Context context)
    {
        super(spriteID,xPos,yPos,width,height,context);
        speed = new PointF(50f,0f);
    }

    @Override
    public void Update(float deltaTime)
    {
        if(position.x > 800)
        {
            speed.x = -50f;
        }

        if(position.x < 100)
        {
            speed.x = 50f;
        }

        this.position.x += speed.x * deltaTime;
        this.position.y += speed.y * deltaTime;
        super.Update(deltaTime);
    }


    @Override
    public void Draw(Canvas canvas)
    {
        super.Draw(canvas);
    }
}
