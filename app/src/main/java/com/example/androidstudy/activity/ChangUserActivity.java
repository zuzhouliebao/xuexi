package com.example.androidstudy.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidstudy.R;
import com.example.androidstudy.entity.User;
import com.example.androidstudy.service.UserService;

public class ChangUserActivity extends AppCompatActivity {
    private EditText mdUser,myCancel;

    private User user;
    private UserService userService;

    private int flag;
    private String value;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_user);

        initData();

        initToolBar();

        initView();

    }

    private void initView() {
        mdUser = findViewById(R.id.md_user);
        mdUser.setText(value);

        myCancel = findViewById(R.id.my_cancel);
        myCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdUser.setText("");
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            title = bundle.getString("title");
            value = bundle.getString("value");
            flag = bundle.getInt("flag");
        }
    }

    //加载选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info,menu);
        return true;
    }

    //菜单项的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.name_save:
                save();
                break;
            case R.id.name_cancel:
                ChangUserActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        Intent intent = new Intent();
        String value = mdUser.getText().toString();
        switch (flag){
            case 1:
                if (TextUtils.isEmpty(value)){
                    Toast.makeText(ChangUserActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("nickname",value);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(ChangUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    ChangUserActivity.this.finish();
                }
                break;
            case 2:
                if (TextUtils.isEmpty(value)){
                    Toast.makeText(ChangUserActivity.this,"简介不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    intent.putExtra("signature",value);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(ChangUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    ChangUserActivity.this.finish();
                }
                break;
        }
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
                ChangUserActivity.this.finish();
            }
        });
    }
}
