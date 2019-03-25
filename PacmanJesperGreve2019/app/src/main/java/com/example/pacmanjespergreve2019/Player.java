package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;

public class Player extends GameObject
{
    private TiltControl controller;
    private boolean invincible = false;
    private float invincibleTimer = 0;
    private PointF targetPosition;

    public Player(int spriteID, float xPos, float yPos,int layer, float width,float height, Context context)
    {
        super(spriteID,xPos,yPos,layer,width,height,context);
        targetPosition = this.position;
        controller = new TiltControl(context);
    }

    @Override
    public void Update(float deltaTime)
    {
        if(!GameActivity.touchControls)
        {
            speed.x = controller.getY() * 3f;
            speed.y = controller.getX() * 3f;

            //TODO:limit speed vector

            if (speed.x > 0)
            {
                //Half speed moving up stream.
                speed.x *= 0.5f;
            }

            //Lower Y bounds
            if (position.y > 1080 - 256 && speed.y > 0)
            {
                speed.y = 0;
            }

            //Upper Y bounds
            if (position.y < 64 && speed.y < 0)
            {
                speed.y = 0;
            }

            //Left X bound
            if (position.x < 32 && speed.x < 0)
            {
                speed.x = 0;
            }

            //Right X bound
            if (position.x > 1920 - 256 && speed.x > 0)
            {
                speed.x = 0;
            }
        }
        else
        {
            //Touch controls:
            float dX = this.position.x - this.targetPosition.x;
            float dY = this.position.y - this.targetPosition.y;

            if(Math.abs(dX) >= 20 || Math.abs(dY) >= 20 )
            {
                //move towards point in a linear motion (Bee line)
                float angle = (float)Math.atan2(dY,dX);
                speed.x = -(float)Math.cos(angle)*5;
                speed.y = -(float)Math.sin(angle)*5;
            }
            else
            {
                speed.x = 0f;
                speed.y = 0f;
            }
        }

        if(!invincible)
        {
            //collisionCheck
            for (GameObject go : GameViewManager.objects)
            {
                if (go.getClass() == com.example.pacmanjespergreve2019.Fish.class)
                {
                    CollisionCheck((Fish) go);
                }
            }
        }

        if(invincible)
        {
            invincibleTimer--;

            ChangeSprite(R.drawable.fish_dead);

            if(invincibleTimer == 0)
            {
                invincible = false;
                ChangeSprite(R.drawable.fish_medium);
            }
        }

        super.Update(deltaTime);
    }

    private void CollisionCheck(Fish fish)
    {
        if(this.getCollisionRect().intersect(fish.getCollisionRect()))
        {
            if(fish.type == 0)
            {
                if(fish.alive)
                {
                    fish.alive = false;
                    GameViewManager.score += 20;
                }
            }
            else
            {
                GameViewManager.lives--;
                invincible = true;
                invincibleTimer = 120;
            }
        }
    }

    private  void OnCollisiton(GameObject other)
    {

    }

    public void SetTargetPosition(PointF position)
    {
        this.targetPosition = position;
    }


    @Override
    public void Draw(Canvas canvas)
    {
        super.Draw(canvas);
    }
}
