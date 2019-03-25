package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.Canvas;

public class WorldTile extends GameObject
{
    boolean changeSprite = false;

    public WorldTile(int spriteID, float xPos, float yPos,int layer, float width,float height, Context context,boolean changeSprite)
    {
        super(spriteID,xPos,yPos,layer,width,height,context);
        this.changeSprite = changeSprite;
    }

    @Override
    public void Update(float deltaTime)
    {
        this.position.x -= GameViewManager.worldSpeed;

        if(this.position.x <= -128)
        {
            if(changeSprite)
            {
                ChangeSprite(GameViewManager.GetRandomSandTop());
            }
            this.position.x += 1920f+128f;
        }
        super.Update(deltaTime);
    }


    @Override
    public void Draw(Canvas canvas)
    {
        super.Draw(canvas);
    }
}
