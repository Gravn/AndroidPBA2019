package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.Canvas;

import java.util.Random;

public class Decor extends GameObject
{
    private Random r;

    public Decor(int spriteID, float xPos, float yPos,int layer, float width,float height, Context context)
    {
        super(spriteID,xPos,yPos,layer,width,height,context);
        r = new Random();
    }

    @Override
    public void Update(float deltaTime)
    {
        this.position.x -= GameViewManager.worldSpeed;

        if(this.position.x <= -128)
        {
            //ChangeSprite(GameViewManager.GetRandomSandTop());
            this.position.x += r.nextInt(512) + 1920f+128;
        }
        super.Update(deltaTime);
    }


    @Override
    public void Draw(Canvas canvas)
    {
        super.Draw(canvas);
    }
}
