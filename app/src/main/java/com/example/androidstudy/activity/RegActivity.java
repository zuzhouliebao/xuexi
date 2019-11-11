package com.example.androidstudy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidstudy.R;
import com.example.androidstudy.utils.MD5Utils;

public class RegActivity extends AppCompatActivity {
    //1. 获取界面上的控件
    //2. buttom的点击事件的监听
    //3. 处理点击事件
    //3.1 获取控件的值
    //3.2 检查数据的有效性
    //3.3 将注册信息存储
    //3.4 跳转到登陆界面
    private EditText etUsername,etPassword,etPwdAgain;
    private Button btnRegister,btnRestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //1. 获取界面上的控件
        initView();
        //2. buttom的点击事件的监听
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
                String username = pref.getString("username","");
                String password = pref.getString("password","");
                Log.d("RegActivity","name is " + username);
                Log.d("RegActivity","password is " + password);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3.1 获取控件的值
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String pwdAgain = etPwdAgain.getText().toString();
                //3.2 检查数据的有效性
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(RegActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(password)||TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(RegActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }else if (!password.equals(pwdAgain)){
                    Toast.makeText(RegActivity.this,"两次密码必须一致",Toast.LENGTH_LONG).show();
                }else if(isExist(username)){
                    Toast.makeText(RegActivity.this,"此用户已存在",Toast.LENGTH_LONG).show();
                }else {
                    savePref(username, MD5Utils.md5(password));
                    Intent intent = new Intent();
                    intent.putExtra("username",username);
                    setResult(RESULT_OK,intent);
                    finish();
//                    startActivityForResult(intent,1);
                }
            }
        });
    }

    private void savePref(String username, String password) {
        SharedPreferences.Editor editor = getSharedPreferences("userInfo",MODE_PRIVATE).edit();
//        editor.putString("username",username);
//        editor.putString("password",password);
        editor.putString(username, password);
        editor.apply();
//        editor.clear();
//        editor.commit();
    }

    private boolean isExist(String username){
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        String pwd = sp.getString(username,"");
        return !TextUtils.isEmpty(pwd);
    }

    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPwdAgain = findViewById(R.id.et_repassword);
        btnRegister = findViewById(R.id.reg_btn);
        btnRestore = findViewById(R.id.restore_btn);
    }
}
