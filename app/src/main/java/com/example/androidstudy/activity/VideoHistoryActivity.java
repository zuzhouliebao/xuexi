package com.example.androidstudy.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.androidstudy.R;
import com.example.androidstudy.adapter.HistoryAdapter;
import com.example.androidstudy.entity.PlayH;
import com.example.androidstudy.service.VideoService;
import com.example.androidstudy.service.VideoServiceImpl;
import com.example.androidstudy.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoHistoryActivity extends AppCompatActivity {
    private RecyclerView rv;
    private HistoryAdapter adapter;

    private List<PlayH> playHList;
    private VideoService service = new VideoServiceImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_history);

        initToolbar();
        initData();
        initView();
    }

    private void initView() {
        rv = findViewById(R.id.us_hs);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);
        adapter = new HistoryAdapter(playHList);
        rv.setAdapter(adapter);
    }

    private void initData() {
        String username = SharedUtils.readValue(this,"loginUser");
        System.out.println("username: " + username);

        playHList = new ArrayList<>();
        playHList = service.get(username);
        System.out.println("history: " + playHList);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("播放记录");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoHistoryActivity.this.finish();
            }
        });
    }


}
