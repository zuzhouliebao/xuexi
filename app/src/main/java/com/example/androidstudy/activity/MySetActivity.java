package com.example.androidstudy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.androidstudy.R;

public class MySetActivity extends AppCompatActivity {
    private LinearLayout modifyPwd,btQuit,setPtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_set);

        initToolbar();

        modifyPwd = findViewById(R.id.mf_pwd);
        btQuit = findViewById(R.id.bt_quit);
        setPtPwd = findViewById(R.id.set_pt_pwd);

        setPtPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySetActivity.this,ProtectPwdctivity.class);
                intent.putExtra("isVis2",true);
                startActivity(intent);
            }
        });

        modifyPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MySetActivity.this,ModifyPwdActivity.class);
                startActivity(intent);
            }
        });

        btQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MySetActivity.this)
                        .setTitle("提示")
                        .setMessage("是否退出登陆")
                        .setPositiveButton("是",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("userInfo",MODE_PRIVATE).edit();
                                editor.putBoolean("isLogin",false);
                                editor.apply();
                                Intent intent = new Intent(MySetActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
                /*SharedPreferences.Editor editor = getSharedPreferences("userInfo",MODE_PRIVATE).edit();
                editor.putBoolean("isLogin",false);
                editor.apply();
                Intent intent = new Intent(MySetActivity.this,MainActivity.class);
                startActivity(intent);*/
            }
        });

    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySetActivity.this.finish();
            }
        });
    }
}
