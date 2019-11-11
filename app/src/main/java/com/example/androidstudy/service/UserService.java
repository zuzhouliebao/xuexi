package com.example.androidstudy.service;

import com.example.androidstudy.entity.User;

public interface UserService {
    User get(String username);
    void save(User user);
    void modify(User user);
}
