package com.example.androidstudy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidstudy.entity.PlayH;
import com.example.androidstudy.entity.Video;
import com.example.androidstudy.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class VideoInfoDaoImpl implements VideoInfoDao{
    private DBHelper helper; //创建数据库、获取数据库对象
    private SQLiteDatabase db; //执行sql语句

    public VideoInfoDaoImpl(Context context){
        helper = DBHelper.getInstance(context);  //创建数据库
    }

    @Override
    public List<PlayH> select(String username) {
        /*List<PlayH> playHS = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TBL_NAME_VIDEO,
                null, null,null,
                null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                PlayH playHInfo = new PlayH();
                playHInfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                playHInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                playHInfo.setVideoTitle(cursor.getString(cursor.getColumnIndex("video_title")));
                playHS.add(playHInfo);
            }while(cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return playHS;*/
        List<PlayH> playHList = new ArrayList<>();
        String sql = "select * from " + DBHelper.TBL_NAME_VIDEO + " where username=?";
        db =  helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if(cursor != null && cursor.moveToFirst()) {
            do {
                PlayH playH = new PlayH();
                playH.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                playH.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                playH.setVideoTitle(cursor.getString(cursor.getColumnIndex("video_title")));
                playHList.add(playH);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return playHList;
    }

    @Override
    public PlayH selectOne(String username,String videoTitle) {
        PlayH playH = null;
        /*db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TBL_NAME_VIDEO,null,"username=? AND video_title=?",
                new String[]{username,videoTitle},null,null,null);*/
        String sql = "select * from " + DBHelper.TBL_NAME_VIDEO + " where video_title=? AND username = ?";
        db =  helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[]{videoTitle,username});
        if (cursor != null && cursor.moveToFirst()){
            playH = new PlayH();
            playH.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            playH.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            playH.setVideoTitle(cursor.getString(cursor.getColumnIndex("video_title")));
            cursor.close();
        }
        db.close();
        return playH;
    }

    @Override
    public void insert(PlayH playH) {
        ContentValues values = new ContentValues();
        values.put("username",playH.getUsername());
        values.put("title",playH.getTitle());
        values.put("video_title",playH.getVideoTitle());

        db = helper.getWritableDatabase();
        db.insert(DBHelper.TBL_NAME_VIDEO,null,values);
        db.close();
    }
}
