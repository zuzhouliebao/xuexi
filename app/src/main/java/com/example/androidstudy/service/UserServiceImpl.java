package com.example.androidstudy.service;

import android.content.Context;

import com.example.androidstudy.dao.UserInfoDao;
import com.example.androidstudy.dao.UserInfoDaoImpl;
import com.example.androidstudy.entity.User;

public class UserServiceImpl implements UserService{
    private UserInfoDao dao;

    public UserServiceImpl(Context context){
        dao = new UserInfoDaoImpl(context);
    }

    @Override
    public User get(String username) {
        return dao.select(username);
    }

    @Override
    public void save(User user) {
        dao.insert(user);
    }

    @Override
    public void modify(User user) {
        dao.updata(user);
    }
}
