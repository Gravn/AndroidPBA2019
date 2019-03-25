package com.example.pacmanjespergreve2019;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbManager extends SQLiteOpenHelper
{
    private static final int dbversion = 1;

    public DbManager(Context context,String dbName)
    {
        super(context,dbName,null,dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE Player (_ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Score REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Player;");
        onCreate(db);
    }

    public void Insert(String playerName,float score)
    {
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("INSERT INTO Player ( _ID , Name , Score ) VALUES (NULL,'Gravn','10')");
        db.execSQL("INSERT INTO PLAYER( _ID , Name , Score ) VALUES (NULL,'"+playerName+"','"+score+"')");
        db.close();
    }


    public String[] GetScoreByIndex(int index)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Player ORDER BY Score DESC LIMIT 0,100;", null);

        if (index >= c.getCount())
        {
            c.close();
            return null;
        }

        c.moveToPosition(index);
        if (c.getString(c.getColumnIndex("Name")) != null && c.getString(c.getColumnIndex("Score")) != null)
        {

            String[] entry = new String[2];
            entry[0] = c.getString(c.getColumnIndex("Name"));
            entry[1] = c.getString(c.getColumnIndex("Score"));
            c.close();
            return entry;
        }
        else
        {
            return null;
        }
    }

    public String returnScore(int index)
    {
        //Hardcoded to top 100
        if(index<0 || index>=100)
        {
            return "OutofRange";
        }
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Player ORDER BY Score ASC LIMIT 0,100;",null);
        String entry = "";

        Log.d("G",index+"<="+c.getCount()+"");
        if(index>=c.getCount())
        {
            c.close();
            return "Empty";
        }

        c.moveToPosition(index);

        if(c.getString(c.getColumnIndex("Name"))!=null && c.getString(c.getColumnIndex("Score"))!=null)
        {
            entry = c.getString(c.getColumnIndex("Name"))+":"+c.getString(c.getColumnIndex("Score"));
        }
        c.close();
        return entry;
    }

}
