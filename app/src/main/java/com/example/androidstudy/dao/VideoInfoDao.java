package com.example.androidstudy.dao;

import com.example.androidstudy.entity.PlayH;
import java.util.List;

public interface VideoInfoDao {
    List<PlayH> select(String username);
    PlayH selectOne(String username,String videoTitle);
    void insert(PlayH playH);
}
