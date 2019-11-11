package com.example.androidstudy.entity;

import java.io.Serializable;

//entity类与数据库表一一对应
public class User implements Serializable {
    private int _id;
    private String username;
    private String nickname;
    private String sex;
    private String signature;

    public User() {
    }

    public User(String username, String nickname, String sex, String signature) {
        this.username = username;
        this.nickname = nickname;
        this.sex = sex;
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
