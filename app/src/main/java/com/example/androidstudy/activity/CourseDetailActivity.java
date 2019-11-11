package com.example.androidstudy.activity;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.service.quicksettings.Tile;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.androidstudy.R;
import com.example.androidstudy.adapter.VideoAdapter;
import com.example.androidstudy.entity.Course;
import com.example.androidstudy.entity.PlayH;
import com.example.androidstudy.entity.Video;
import com.example.androidstudy.service.VideoService;
import com.example.androidstudy.service.VideoServiceImpl;
import com.example.androidstudy.utils.IOUtils;
import com.example.androidstudy.utils.SharedUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {
    private VideoView videoView;
    private ImageView ivVideo;
    private TextView tvIntro;
    private RecyclerView rvVideo;

    private Course course;
    private List<Video> videos;
    private VideoAdapter adapter;

    private MediaController controller;

    private VideoService service;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initData();
        initView();
        loadFirstFrame();

        initToolBar();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            course = (Course) bundle.getSerializable("course");
        }

        // 2. 从json文件中获取视频的描述数据
        videos = new ArrayList<>();
        try {
            // 2.1 获取json文件中的所有数据集合
            InputStream is = getResources().getAssets().open("course.json");
            String json = IOUtils.convert(is, StandardCharsets.UTF_8);
            videos = IOUtils.convert(json, Video.class);

            // 2.2 筛选出course的chapterId对应的视频信息集合
            Iterator<Video> it = videos.iterator();
            while(it.hasNext()) {
                Video video = it.next();
                if(video.getChapterId() != course.getId()) {
                    it.remove();
                }
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        title = getIntent().getStringExtra("title");
    }

    private void initView(){
        videoView = findViewById(R.id.video_view);
        controller = new MediaController(this);
        videoView.setMediaController(controller);

        ivVideo = findViewById(R.id.iv_video);
        tvIntro = findViewById(R.id.tv_intro);
        rvVideo = findViewById(R.id.rv_video);

        tvIntro.setText(course.getIntro());

        adapter = new VideoAdapter(videos);
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        rvVideo.setAdapter(adapter);

        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 设置选中项，并通过notifyDataSetChanged()更新UI
                adapter.setSelected(position);
                adapter.notifyDataSetChanged();  //更新UI


                // 获取video对象数据，且初始化videoview
                Video video = videos.get(position);
                if(videoView.isPlaying()) {
                    videoView.stopPlayback();
                }

                if(TextUtils.isEmpty(video.getVideoPath())) {
                    Toast.makeText(CourseDetailActivity.this, "本地没有此视频，暂时无法播放", Toast.LENGTH_SHORT).show();
                    return;
                }

                //播放
                videoView.setVisibility(View.VISIBLE);
                ivVideo.setVisibility(View.GONE);
                String uri = "android.resource://" + getPackageName() + "/" +R.raw.video101;
                videoView.setVideoPath(uri);
                videoView.start();

                PlayH playH = new PlayH();
                String username = SharedUtils.readValue(CourseDetailActivity.this, "loginUser");
                service = new VideoServiceImpl(CourseDetailActivity.this);
                playH.setUsername(username);
                playH.setTitle(video.getTitle());
                playH.setVideoTitle(video.getVideoTitle());
                System.out.println(playH);
                if (service.getOne(username,playH.getVideoTitle()) == null) {
                    System.out.println(playH.toString());
                    System.out.println(playH);
                    service.save(playH);
                }

            }
        });
    }

    //加载视频的首帧图像
    private void loadFirstFrame(){
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video101));
        bitmap = retriever.getFrameAtTime();
        ivVideo.setImageBitmap(bitmap);
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseDetailActivity.this.finish();
            }
        });
    }
}
