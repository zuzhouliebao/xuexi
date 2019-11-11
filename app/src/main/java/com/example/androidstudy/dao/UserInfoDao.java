package com.example.androidstudy.dao;

import com.example.androidstudy.entity.User;

import java.util.List;

public interface UserInfoDao {
    List<User> select();
    User select(String username);
    void insert(User userInfo);
    void updata(User userInfo);
    void delete(User userInfo);
}
