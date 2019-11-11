package com.example.androidstudy.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.androidstudy.R;
import com.example.androidstudy.adapter.ExerciseDetailAdapter;
import com.example.androidstudy.adapter.RecyclerAdapter;
import com.example.androidstudy.entity.SubjectExer;
import com.example.androidstudy.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailActivity extends AppCompatActivity
        implements ExerciseDetailAdapter.OnSelectListener {
    private int id;
    private String title;
    private List<SubjectExer> details;

    private RecyclerView listDetailTitle;
    private ExerciseDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        initData();
        initView();

        initToolbar();
    }

    private void initData() {
        id = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");

        details = new ArrayList<>();
        InputStream is = null;
        try {
            is = getResources().getAssets().open("chapter" + id + ".xml");
            details = IOUtils.getXmlContents(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        listDetailTitle = findViewById(R.id.list_detail_title);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listDetailTitle.setLayoutManager(manager);
        adapter = new ExerciseDetailAdapter(details,this);
        listDetailTitle.setAdapter(adapter);
    }


    @Override
    public void onSelectA(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
         SubjectExer exer = details.get(position);

         if (exer.getAnswer() != 1){
             exer.setSelect(1);
         }else {
             exer.setSelect(0);
         }

         switch (exer.getAnswer()){
             case 1:
                 ivA.setImageResource(R.mipmap.ic_exercise_answer_right);
                 break;
             case 2:
                 ivB.setImageResource(R.mipmap.ic_exercise_answer_right);
                 ivA.setImageResource(R.mipmap.ic_exercise_answer_error);
                 break;
             case 3:
                 ivC.setImageResource(R.mipmap.ic_exercise_answer_right);
                 ivA.setImageResource(R.mipmap.ic_exercise_answer_error);
                 break;
             case 4:
                 ivD.setImageResource(R.mipmap.ic_exercise_answer_right);
                 ivA.setImageResource(R.mipmap.ic_exercise_answer_error);
                 break;
         }
//         ExerciseDetailAdapter.setABCDEnable(false,ivA,ivB,ivC,ivD);
    }

    @Override
    public void onSelectB(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        SubjectExer exer = details.get(position);

        if (exer.getAnswer() != 2){
            exer.setSelect(2);
        }else {
            exer.setSelect(0);
        }

        switch (exer.getAnswer()){
            case 1:
                ivA.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivB.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
            case 2:
                ivB.setImageResource(R.mipmap.ic_exercise_answer_right);
                break;
            case 3:
                ivC.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivB.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
            case 4:
                ivD.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivB.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
        }
//                 ExerciseDetailAdapter.setABCDEnable(false,ivA,ivB,ivC,ivD);

    }

    @Override
    public void onSelectC(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        SubjectExer exer = details.get(position);

        if (exer.getAnswer() != 3){
            exer.setSelect(3);
        }else {
            exer.setSelect(0);
        }

        switch (exer.getAnswer()){
            case 1:
                ivA.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivC.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
            case 2:
                ivB.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivC.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
            case 3:
                ivC.setImageResource(R.mipmap.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivC.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
        }
//                 ExerciseDetailAdapter.setABCDEnable(false,ivA,ivB,ivC,ivD);

    }

    @Override
    public void onSelectD(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        SubjectExer exer = details.get(position);

        if (exer.getAnswer() != 4){
            exer.setSelect(4);
        }else {
            exer.setSelect(0);
        }

        switch (exer.getAnswer()){
            case 1:
                ivA.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivD.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
            case 2:
                ivB.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivD.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
            case 3:
                ivC.setImageResource(R.mipmap.ic_exercise_answer_right);
                ivD.setImageResource(R.mipmap.ic_exercise_answer_error);
                break;
            case 4:
                ivD.setImageResource(R.mipmap.ic_exercise_answer_right);
                break;
        }
//                 ExerciseDetailAdapter.setABCDEnable(false,ivA,ivB,ivC,ivD);

    }

    private void initToolbar() {
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
                ExerciseDetailActivity.this.finish();
            }
        });
    }
}
