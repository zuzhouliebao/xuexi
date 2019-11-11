package com.example.androidstudy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class ProtectPwdctivity extends AppCompatActivity {
    private EditText etMyUsername,etName;
    private Button btVer,findPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect_pwdctivity);

        initToolbar();
        initControl();

        final Intent intent = getIntent();
        Boolean isVis = intent.getBooleanExtra("isVis",false);
        Boolean isVis2 = intent.getBooleanExtra("isVis2",false);
        if (isVis2.equals(true)){
            btVer.setVisibility(View.VISIBLE);
        }

        if (isVis.equals(true)){
            etMyUsername.setVisibility(View.VISIBLE);
            findPwd.setVisibility(View.VISIBLE);
        }

        findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etMyUsername.getText().toString();
                String proPwd = etName.getText().toString();

                SharedPreferences pro = getSharedPreferences("proInfo",MODE_PRIVATE);
                String name = pro.getString(user,"");
                System.out.println("name:  "     + name);

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(proPwd)){
                    Toast.makeText(ProtectPwdctivity.this,"不能为空",Toast.LENGTH_LONG).show();
                }else if (!proPwd.equals(name)){
                    Toast.makeText(ProtectPwdctivity.this,"姓名错误",Toast.LENGTH_LONG).show();
                }else if ( proPwd.equals(name)){
                    SharedPreferences pref2 = getSharedPreferences("userInfo",MODE_PRIVATE);
                    String password = pref2.getString(user,"");
                    Intent intent1 = new Intent(ProtectPwdctivity.this,ModifyPwdActivity.class);
                    intent1.putExtra("isPwd",true);
                    intent1.putExtra("username",user);
                    startActivity(intent1);
                    finish();
                }
            }
        });

        btVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
                String name = etName.getText().toString();
                String username = pref.getString("loginUser","");
                System.out.println("username: " + username);
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(ProtectPwdctivity.this,"不能为空",Toast.LENGTH_LONG).show();
                }else{
                 savePro(username,name);
//                 Intent intent = new Intent();
                 finish();
                }
            }
        });
    }

    //保存登陆状态
    private void savePro(String username, String name) {
        SharedPreferences.Editor editor = getSharedPreferences("proInfo",MODE_PRIVATE).edit();
//        editor.putString("username",username);
//        editor.putString("name",name);
        editor.putString(username, name);
        editor.apply();
        /*editor.clear();
        editor.commit();*/
    }

    private void initControl() {
        etMyUsername = findViewById(R.id.et_my_username);
        btVer = findViewById(R.id.bt_ver);
        etName = findViewById(R.id.et_name);
        findPwd = findViewById(R.id.find_pwd);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("设置密保");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProtectPwdctivity.this.finish();
            }
        });
    }
}
