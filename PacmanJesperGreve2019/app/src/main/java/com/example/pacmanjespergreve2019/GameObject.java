package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class GameObject
{
    Bitmap sprite;
    PointF position;
    PointF size;
    Context context;
    Rect spriteRect;
    RectF objectRect;


    public GameObject(int spriteID, float xPos, float yPos, float width, float height, Context context )
    {
        this.context = context;
        this.position = new PointF(xPos,yPos);
        this.size = new PointF(width,height);
        sprite = BitmapFactory.decodeResource(context.getResources(),spriteID);
        this.spriteRect = new Rect(0,0,sprite.getWidth(),sprite.getHeight());
        this.objectRect = new RectF(xPos,yPos,width,height);

    }

    protected RectF getCollisionRect()
    {
        return new RectF(position.x,position.y,position.x+size.x,position.y+size.y);
    }

    public void ChangeSprite(int spriteID)
    {
        this.sprite = BitmapFactory.decodeResource(context.getResources(),spriteID);
    }

    protected void Draw(Canvas canvas)
    {
        this.objectRect = new RectF(position.x,position.y,position.x+size.x,position.y+size.y);

        canvas.drawBitmap(sprite,spriteRect,objectRect,null);
        canvas.drawBitmap(sprite,position.x,position.y,null);

        if(GameViewManager.debugging)
        {
            Paint p = new Paint();
            p.setColor(Color.RED);
            p.setStyle(Paint.Style.STROKE);

            canvas.drawRect(getCollisionRect(), p);
        }
    }

    protected void Update(float deltaTime)
    {

    }

    protected void Destroy(GameObject go)
    {
        GameViewManager.objects.remove(go);
    }
}
