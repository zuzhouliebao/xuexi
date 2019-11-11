package com.example.androidstudy.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.androidstudy.R;
import com.example.androidstudy.adapter.ExerciseAdapter;
import com.example.androidstudy.entity.Exer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class TitleFragment extends Fragment {
    private static final String ARG_PARAM = "param";

    private static TitleFragment tf;
    private ListView lv;
    private String mParam;

    private OnFragmentInteractionListener mListener;

    public static TitleFragment getTitleFragment() {
        if (tf == null){
            tf = new TitleFragment();
        }
        return tf;
    }

    public TitleFragment() {
    }

    public static TitleFragment newInstance(String param) {
        TitleFragment fragment = new TitleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    String[] data = {"长江","黄河"};
    List<Exer> exercises;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        initData();
        //1.获取列表控件
        lv = view.findViewById(R.id.list_view);
        //2.创建集合类控件需要的adaper数据适配器，（作用：UI与arrayList数据的桥梁）
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                getActivity(),android.R.layout.simple_list_item_1,data
//        );
        ExerciseAdapter adapter = new ExerciseAdapter(getActivity(),exercises);
        //3.设置ListView的Adapter
        lv.setAdapter(adapter);
        //4.listView中的item的事件监听
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id) {

            }
        });
        return view;
    }

    private void initData(){
        exercises = new ArrayList<>();

        try {
            InputStream input = getResources().getAssets().open("exercise_title.json");

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String str = result.toString(StandardCharsets.UTF_8.name());

            exercises = JSON.parseArray(str,Exer.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateData(){
        for (int i = 0; i < 15; i++){
            Exer exercise = new Exer();
            exercise.setId(i + 1);
            switch (i){
                case 0:
                    exercise.setTitle("第1章");
                    exercise.setSubTitle("共5题");
//                    exercise.setBackground();
                    break;
                case 1:
                    exercise.setTitle("第2章");
                    exercise.setSubTitle("共5题");
//                    exercise.setBackground();
                    break;
                case 2:
                    exercise.setTitle("第3章");
                    exercise.setSubTitle("共5题");
//                    exercise.setBackground();
                    break;
                case 3:
                    exercise.setTitle("第3章");
                    exercise.setSubTitle("共5题");
//                    exercise.setBackground();
                    break;
            }
            exercises.add(exercise);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
