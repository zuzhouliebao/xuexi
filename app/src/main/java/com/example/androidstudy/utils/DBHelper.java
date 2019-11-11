package com.example.androidstudy.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "course.json.db";
    public static final int DB_VERSION = 2;
    public static final String TBL_NAME_USER = "tb_user";
    public static final String TBL_USER = "create table if not exists " +
            TBL_NAME_USER +
            "(_id integer primary key autoincrement, " +
            "user_name varchar, " +
            "nick_name varchar, " +
            "sex varchar, " +
            "signature varchar)";

    public static final String TBL_NAME_VIDEO = "tb_video";
    public static final String TBL_VIDEO = "create table if not exists " +
            TBL_NAME_VIDEO +
            "(username varchar, " +
            "title varchar, " +
            "video_title varchar)";
    private static DBHelper helper;

    private DBHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static DBHelper getInstance(Context context){
        if (helper == null){
            helper = new DBHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
             db.execSQL(TBL_USER);
             db.execSQL(TBL_VIDEO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("drop table if exists " + TBL_NAME_USER);
        db.execSQL("drop table if exists " + TBL_NAME_VIDEO);
      onCreate(db);
    }
}
