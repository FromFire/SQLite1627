package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public  class  DBHelper extends SQLiteOpenHelper {
    public  DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        //constructor be used to create a new database.
        super(context,name,factory,version);
    }

    public void onCreate(SQLiteDatabase db){
        //creat a table name of user
        db.execSQL("create table if not exists user(_id integer " +
                "primary key autoincrement,"+"name text not null,"+"email text not null unique,"+
                "mNumber integer not null unique)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     //abstract class  be  used to upgrade
    }
}
