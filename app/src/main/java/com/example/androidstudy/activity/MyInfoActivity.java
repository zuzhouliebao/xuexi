package com.example.androidstudy.activity;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.androidstudy.R;
import com.example.androidstudy.entity.User;
import com.example.androidstudy.service.UserService;
import com.example.androidstudy.service.UserServiceImpl;
import com.example.androidstudy.utils.SharedUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MyInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MODIFY_NICKNAME = 1;
    private static final int MODIFY_SIGNATURE = 2;
    private static final int PICK_IMAGE = 3;

    private LinearLayout myAvatar,myName,myNick,mySex,mySignature;
    private TextView myNameBg,myNickBg,mySexBg,mySignatureBg;
    private ImageView myAvatarBg;

    private User user;
    private UserService service;
    private String spUsername;

    private static final int REQUEST_READ_PHOTO = 13;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        initData();
        initView();
        initToolbar();


    }

    private void initData() {
         spUsername = SharedUtils.readValue(this,"loginUser");

         service = new UserServiceImpl(this);
         user = service.get(spUsername);        // 从数据库读取
         user = readFromInternal();             // 从内部存储读取
         user = readPrivateExStorage();
         user = readPublicExternalStorage();
         if (user == null){
             user = new User();
             user.setUsername(spUsername);
             user.setNickname("我的课程");
             user.setSex("男");
             user.setSignature("我的课程");
             service.save(user);
             saveTOInternal(user);
             savePrivateExStorage(user);
             savePublicExternalStorage(user);
         }
    }


    private void initView() {
        myAvatar = findViewById(R.id.my_avatar);
        myName = findViewById(R.id.my_name);
        myNick = findViewById(R.id.my_nick);
        mySex = findViewById(R.id.my_sex);
        mySignature = findViewById(R.id.my_signature);

        myAvatarBg = findViewById(R.id.my_avatar_bg);
        myNameBg = findViewById(R.id.my_name_bg);
        myNickBg = findViewById(R.id.my_nick_bg);
        mySexBg = findViewById(R.id.my_sex_bg);
        mySignatureBg = findViewById(R.id.my_signature_bg);

        myNameBg.setText(user.getUsername());
        myNickBg.setText(user.getNickname());
        mySexBg.setText(user.getSex());
        mySignatureBg.setText(user.getSignature());

        myNick.setOnClickListener(this);
        mySex.setOnClickListener(this);
        mySignature.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.title_toolbar);
        toolbar.setTitle("个人设置");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyInfoActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_nick:
                modifyNickname();
                break;
            case R.id.my_sex:
                modifySex();
                break;
            case R.id.my_signature:
                modifySignature();
                break;
        }
    }

    private void modifySignature() {
        String signature = mySignatureBg.getText().toString();

        Intent intent = new Intent(MyInfoActivity.this,ChangUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title","设置签名");
        bundle.putString("value",signature);
        bundle.putInt("flag",2);
        intent.putExtras(bundle);

        startActivityForResult(intent,2);
    }

    private void modifySex() {
        final String[] datas = {"男","女"};
        String sex = mySexBg.getText().toString();
        final List<String> sexs = Arrays.asList(datas);
        int selected = sexs.indexOf(sex);


        new AlertDialog.Builder(this)
                .setTitle("性别")
                .setSingleChoiceItems(datas, selected,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String sex = datas[i];
                                mySexBg.setText(sex);
                                user.setSex(sex);
                                service.modify(user);
                                saveTOInternal(user);

                                dialogInterface.dismiss();
                            }
                        }).show();
    }

    private void modifyNickname() {
        String nickname = myNickBg.getText().toString();

        Intent intent = new Intent(MyInfoActivity.this,ChangUserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title","设置昵称");
        bundle.putString("value",nickname);
        bundle.putInt("flag",1);
        intent.putExtras(bundle);

        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != RESULT_OK){
            Toast.makeText(MyInfoActivity.this,"未知错误",Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == 1){
            String value = data.getStringExtra("nickname");
            myNickBg.setText(value);
            user.setNickname(value);
        }else if (requestCode == 2){
            String value = data.getStringExtra("signature");
            mySignatureBg.setText(value);
            user.setSignature(value);
        }else if (requestCode == 3){
            try {
                Uri uri = data.getData();
                Bitmap header = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                //
                myAvatarBg.setImageBitmap(header);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        service.modify(user);
        saveTOInternal(user);
    }

    // 读取
    private User readFromInternal(){
        User user = null;

        try {
            FileInputStream in = this.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data,User.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private static final String FILE_NAME = "userinfo.txt";
    // 2. 内部存储
    // 保存
    private void saveTOInternal(User user){
        // 内部存储目录：data/data/包名/files/
        try {
            // 1. 打开文件输出流
            FileOutputStream out = this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            // 2. 创建BufferedWriter对戏
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            // 3. 写入数据
            writer.write(JSON.toJSONString(user));
            // 4. 关闭输出流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePrivateExStorage(User user){
        try {
            // 1. 打开文件输出流
            File file = new File(getExternalFilesDir(""),FILE_NAME);
            FileOutputStream out = new FileOutputStream(file);
            // 2. 创建BufferedWriter对戏
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            // 3. 写入数据
            writer.write(JSON.toJSONString(user));
            // 4. 关闭输出流
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User readPrivateExStorage(){
        User user = null;
        try {
            File file = new File(getExternalFilesDir(""),FILE_NAME);
            if (!file.exists()){
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data,User.class);
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private static final int REQUEST_READ_USERINFO = 101;
    private static final int REQUEST_WRITE_USERINFO = 102;
    private void savePublicExternalStorage(User user){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                              != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_USERINFO);
            }
            return;
        }
        try {
            // 1. 打开文件输出流
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
            FileOutputStream out = new FileOutputStream(file);
            // 2. 创建BufferedWriter对戏
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            // 3. 写入数据
            writer.write(JSON.toJSONString(user));
            writer.flush();
            // 4. 关闭输出流
            writer.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User readPublicExternalStorage(){
        User user = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_READ_USERINFO);
            }
            return user;
        }
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
            if (!file.exists()){
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data,User.class);
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    // 权限的回调
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 1. 判断申请结果
        if (grantResults.length == 0 ||
            grantResults[0] != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"申请权限被拒绝，无法执行操作",Toast.LENGTH_SHORT).show();
            return;
        }
        // 2. 如果被允许，根据requestCode进行相应处理
        if (requestCode == REQUEST_READ_USERINFO){
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
                if (!file.exists()){
                    return;
                }
                FileInputStream in = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String data = reader.readLine();
                user = JSON.parseObject(data,User.class);
                reader.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_WRITE_USERINFO){
            try {
                // 1. 打开文件输出流
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
                FileOutputStream out = new FileOutputStream(file);
                // 2. 创建BufferedWriter对戏
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                // 3. 写入数据
                writer.write(JSON.toJSONString(user));
                writer.flush();
                // 4. 关闭输出流
                writer.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == REQUEST_READ_PHOTO) {
            choosePhoto();
        }
        }

    private void modifyHeadIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_READ_PHOTO);
                return;
            }
        }
        choosePhoto();
    }

    void choosePhoto() {
        // Intent.ACTION_PICK：隐性的Intent
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, PICK_IMAGE);
    }

}
