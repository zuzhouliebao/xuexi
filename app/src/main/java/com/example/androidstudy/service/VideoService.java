package com.example.androidstudy.service;

import com.example.androidstudy.entity.PlayH;
import com.example.androidstudy.entity.Video;

import java.util.List;

public interface VideoService {
    List<PlayH> get(String username);
    PlayH getOne(String username,String videoTitle);
    void save(PlayH playH);
}
