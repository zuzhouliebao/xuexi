package com.example.androidstudy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidstudy.R;
import com.example.androidstudy.utils.MD5Utils;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etUsername,etPassword,etMyUsername;
    private Button btFindPwd,btBos,btLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
//        initData();
        initControl();

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        etUsername.setText(username);

        //注册按钮
        btBos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegActivity.class);
                startActivityForResult(intent,1);
            }
        });

        //登陆按钮
        btLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this,RegActivity.class);
//                startActivity(i);
                String username = etUsername.getText().toString();
                String pwd = etPassword.getText().toString();
                SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
                String password = pref.getString(username,"");
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(MainActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(MainActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }else if (!MD5Utils.md5(pwd).equals(password)){
                    Toast.makeText(MainActivity.this,"密码错误!",Toast.LENGTH_LONG).show();
                }else if (MD5Utils.md5(pwd).equals(password)){
                    Toast.makeText(MainActivity.this,"登陆成功!",Toast.LENGTH_LONG).show();
                    saveLoginStatus(username,true);
                    /*SharedPreferences pro = getSharedPreferences("proInfo",MODE_PRIVATE);
                    String username1 = pro.getString("username","");
                    System.out.println("username: " + username);*/
//                    Intent intent = new Intent(MainActivity.this,TopActivity.class);
                    Intent intent = new Intent();
//                    intent.putExtra("isLogin",true);
//                    intent.putExtra("loginUser",username);
                    setResult(RESULT_OK,intent);
//                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        });

        btFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ProtectPwdctivity.class);
//                etMyUsername.setVisibility(view.VISIBLE);
                intent.putExtra("isVis",true);
               startActivity(intent);
            }
        });

    }

    private void initControl() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btFindPwd = findViewById(R.id.bt_find_pwd);
        btBos = findViewById(R.id.bt_bos);
        btLog = findViewById(R.id.bt_log);
        btFindPwd = findViewById(R.id.bt_find_pwd);
        etMyUsername = findViewById(R.id.et_my_username);
    }

    //保存登陆状态
    private void saveLoginStatus(String username,boolean isLogin){
        getSharedPreferences("userInfo",MODE_PRIVATE).edit().putString("loginUser",username).putBoolean("isLogin",isLogin).apply();
    }
//    private void initData() {
//        String username = readPref();
//        if (!TextUtils.isEmpty(username)){
//            etUsername.setText(username);
//        }
//    }
//
//    private String readPref() {
//        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
//        return sp.getString("username","");
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            String username= data.getStringExtra("username");
            if (!TextUtils.isEmpty(username)){
                etUsername.setText(username);
            }
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("登陆");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.finish();
            }
        });
    }


}
