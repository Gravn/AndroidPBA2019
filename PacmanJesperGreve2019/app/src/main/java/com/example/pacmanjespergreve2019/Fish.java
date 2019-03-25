package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.PointF;

import java.util.Random;

public class Fish extends GameObject
{
    float targetYpos;
    Random r;
    public boolean alive = true;
    public int type;

    public Fish(int spriteID, float xPos, float yPos,int layer, float width,float height, Context context,int type)
    {
        super(spriteID,xPos,yPos,layer,width,height,context);
        r = new Random();
        targetYpos = r.nextInt(800);
        this.type = type;
        speed.x = -GameViewManager.worldSpeed + (r.nextInt(5)-2);
    }

    protected void Update(float deltaTime)
    {
        //ALL NPC fish swim up and down, unpredictably.
        //They get a random y target, go there, and find a new target.

        if(alive)
        {
            //If distance/difference to target Y is above treshold:
            if (Math.abs(this.position.y - targetYpos) > 5f)
            {
                //Above or under target?
                if (this.position.y > targetYpos)
                {
                    this.speed.y = -2f;
                }
                else
                {
                    this.speed.y = 2f;
                }
            }
            else
            {
                //Close to target, find a new target.a
                targetYpos = r.nextInt(800);
            }

        }
        else
        {
            this.speed.x = -GameViewManager.worldSpeed;
            this.speed.y = 4f;
            ChangeSprite(R.drawable.fish_small_dead);
        }

        if (this.position.x < -128)
        {
            Respawn();
        }

        super.Update(deltaTime);
    }

    protected void Respawn()
    {
        if(this.type == 0)
        {
            ChangeSprite(R.drawable.fish_small);
            alive = true;
            speed.x = -GameViewManager.worldSpeed + (r.nextInt(5)-2);
            r = new Random();
        }
        this.position.y = 500;
        this.position.x += 1920 + r.nextInt(1024);
    }
}
