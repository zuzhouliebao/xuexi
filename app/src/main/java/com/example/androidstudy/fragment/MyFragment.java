package com.example.androidstudy.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidstudy.activity.MainActivity;
import com.example.androidstudy.activity.MyInfoActivity;
import com.example.androidstudy.activity.MySetActivity;
import com.example.androidstudy.R;
import com.example.androidstudy.activity.VideoHistoryActivity;

public class MyFragment extends Fragment {
    private static MyFragment myf;
    private Context mContext;
    private TextView tvUsername;
    private LinearLayout headLayout,historyLayout,settinglayout;

    private boolean isLogin;



    public static MyFragment getMyFragment() {
        if (myf == null){
            myf = new MyFragment();
        }
        return myf;
    }

    public static MyFragment newInstance(){
        MyFragment fragment = new MyFragment();
        //从activity给fragment传参数的方法
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    private boolean checkLoginStatus(){
        SharedPreferences sp = mContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin",false);
    }

    //初始化fragment的xml界面上的所有控件和数据，相当于activity的oncreate（）方法作用
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //1.获取fragment的父activity，以及目前的登陆状态
        this.mContext = getContext();
        this.isLogin = checkLoginStatus();

        //2.获取fragment界面上需要处理的控件对象
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        tvUsername = view.findViewById(R.id.tv_username);
        setUsername(isLogin);

        settinglayout = view.findViewById(R.id.my_set);
        headLayout = view.findViewById(R.id.ll_head);
        historyLayout = view.findViewById(R.id.rl_history);

        headLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){
                    Intent intent = new Intent(mContext, MyInfoActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivityForResult(intent,1);
                }
            }
        });

        historyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){
                    Intent intent = new Intent(mContext, VideoHistoryActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Toast.makeText(mContext, "请先登陆", Toast.LENGTH_SHORT).show();
                }
            }
        });

        settinglayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){
                    Intent intent = new Intent(mContext, MySetActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(mContext, "请先登陆", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void setUsername(boolean isLogin){
        if (isLogin){
            tvUsername.setText(readLoginInfo());
        }else {
            tvUsername.setText("点击登录");
        }
    }

    private String readLoginInfo(){
        SharedPreferences sp = mContext.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        return sp.getString("loginUser","");
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data ){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            isLogin=data.getBooleanExtra("isLogin",false);
            setUsername(isLogin);
        }
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayout settingLayout = (LinearLayout) getActivity().findViewById(R.id.my_set);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MySetActivity.class);
                startActivity(intent);
            }
        });
    }*/
}
