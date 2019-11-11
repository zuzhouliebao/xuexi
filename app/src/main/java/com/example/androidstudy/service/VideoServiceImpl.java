package com.example.androidstudy.service;

import android.content.Context;

import com.example.androidstudy.dao.VideoInfoDao;
import com.example.androidstudy.dao.VideoInfoDaoImpl;
import com.example.androidstudy.entity.PlayH;
import com.example.androidstudy.entity.Video;

import java.util.List;

public class VideoServiceImpl implements VideoService{
    private VideoInfoDao dao;

    public VideoServiceImpl(Context context){
        dao = new VideoInfoDaoImpl(context);
    }

    @Override
    public List<PlayH> get(String username) {
        return dao.select(username);
    }

    @Override
    public PlayH getOne(String username,String videoTitle){
        return dao.selectOne(username,videoTitle);
    }

    @Override
    public void save(PlayH playH) {
        dao.insert(playH);
    }
}
