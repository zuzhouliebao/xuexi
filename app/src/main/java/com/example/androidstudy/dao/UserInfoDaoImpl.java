package com.example.androidstudy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidstudy.utils.DBHelper;
import com.example.androidstudy.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfoDaoImpl implements UserInfoDao{
    private DBHelper helper; //创建数据库、获取数据库对象
    private SQLiteDatabase db; //执行sql语句

    public UserInfoDaoImpl(Context context){
        helper = DBHelper.getInstance(context);  //创建数据库
    }

    @Override
    public List<User> select() {
        List<User> users = new ArrayList<>();
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TBL_NAME_USER,
                null, null,null,
                null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                User userInfo = new User();
                userInfo.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
                userInfo.setNickname(cursor.getString(cursor.getColumnIndex("nick_name")));
                userInfo.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                userInfo.setSignature(cursor.getString(cursor.getColumnIndex("signature")));

                users.add(userInfo);
            }while(cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return users;
    }

    @Override
    public User select(String username) {
//        String sql = "select * from " + DBHelper.TBL_NAME_USER + " where user_name=?";
        User user = null;
        db = helper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TBL_NAME_USER,null,"user_name=?",
                new String[]{username},null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            user = new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            user.setNickname(cursor.getString(cursor.getColumnIndex("nick_name")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            user.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
            cursor.close();
        }
        db.close();
        return user;
    }

    @Override
    public void insert(User userInfo) {
        ContentValues values = new ContentValues();
        values.put("user_name",userInfo.getUsername());
        values.put("nick_name",userInfo.getNickname());
        values.put("sex",userInfo.getSex());
        values.put("signature",userInfo.getSignature());

        db = helper.getWritableDatabase();
        db.insert(DBHelper.TBL_NAME_USER,null,values);

        /*String sql = "insert into " + DBHelper.TBL_NAME_USER + " values(null, ?, ?, ? ,?)";
        db.execSQL(sql,new String[]{
                userInfo.getUsername(),
                userInfo.getNickname(),
                userInfo.getSex(),
                userInfo.getSignature()
        });*/
        db.close();
    }

    @Override
    public void updata(User userInfo) {
        ContentValues values = new ContentValues();
        values.put("user_name",userInfo.getUsername());
        values.put("nick_name",userInfo.getNickname());
        values.put("sex",userInfo.getSex());
        values.put("signature",userInfo.getSignature());

        db = helper.getWritableDatabase();
        db.update(DBHelper.TBL_NAME_USER,values,"user_name = ?",new String[]{userInfo.getUsername()});

        db.close();
    }

    @Override
    public void delete(User userInfo) {
        db = helper.getWritableDatabase();
        db.delete(DBHelper.TBL_NAME_USER,"user_name = ?",new String[]{userInfo.getUsername()});

        db.close();
    }
}
