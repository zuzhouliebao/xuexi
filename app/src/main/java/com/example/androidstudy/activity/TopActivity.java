package com.example.androidstudy.activity;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.androidstudy.R;
import com.example.androidstudy.fragment.FindFragment;
import com.example.androidstudy.fragment.MainFragment;
import com.example.androidstudy.fragment.MessageFragment;
import com.example.androidstudy.fragment.MyFragment;
import com.example.androidstudy.fragment.RecyclerFragment;
import com.example.androidstudy.fragment.TitleFragment;

public class TopActivity extends AppCompatActivity {
    private SparseArray<Fragment> fragments;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);



        initTitles();
        initFragment();

        final RadioGroup rg_main = (RadioGroup) findViewById(R.id.rg_main);
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0;i<rg_main.getChildCount();i++){
                    RadioButton rb = (RadioButton)group.getChildAt(i);
                    if(rb.isChecked()){
                        setIndexSelectedTwo(i);
                        break;
                    }
                }
            }
        });
    }

    private SparseArray<String> titles;
    private void initTitles() {
        titles = new SparseArray<>();
        titles.put(R.id.rb_my,"我的");
        titles.put(R.id.rb_message,"消息");
        titles.put(R.id.rb_main,"首页");
        titles.put(R.id.rb_title,"练习");
    }

    private void initFragment(){
        fragments = new SparseArray<>();
        fragments.put(R.id.rb_main, MainFragment.getMainFragment());
//        fragments.put(R.id.rb_title, TitleFragment.newInstance("Activity向Fragment传值"));
        fragments.put(R.id.rb_title, RecyclerFragment.newInstance("Activity向Fragment传值"));
        fragments.put(R.id.rb_message, MessageFragment.getMessageFragment());
        fragments.put(R.id.rb_my, MyFragment.getMyFragment());
        changeFragment(fragments.get(R.id.rb_main));
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment , fragment);
        transaction.commit();
    }

    private void setIndexSelectedTwo(int index) {
        switch (index)
        {
            case 0:
                changeFragment(new MainFragment().getMainFragment());
                break;
            case 1:
                changeFragment(new MessageFragment().getMessageFragment());
                break;
            case 2:
                changeFragment(new RecyclerFragment().getRecyclerFragment());
                break;
            case 3:
                changeFragment(new MyFragment().getMyFragment());
                break;
            default:
                break;
        }
    }

    /*private void setToolbar(int checkedId){
        if (checkedId == R.id.rb_my){
             toolbar.setVisibility(View.GONE);
        }else {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle(titles.get(checkedId));
        }
    }*/
}
