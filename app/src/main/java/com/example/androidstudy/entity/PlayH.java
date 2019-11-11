package com.example.androidstudy.entity;

import java.io.Serializable;

public class PlayH implements Serializable {
    private String username;
    private String title;
    private String videoTitle;

    public PlayH() {
    }

    public PlayH(String username, String title, String videoTitle) {
        this.username = username;
        this.title = title;
        this.videoTitle = videoTitle;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    @Override
    public String toString() {
        return "PlayH{" +
                ", username='" + username + '\'' +
                ", title='" + title + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                '}';
    }
}
