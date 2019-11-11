package com.example.androidstudy.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.androidstudy.R;
import com.example.androidstudy.activity.ExerciseDetailActivity;
import com.example.androidstudy.activity.MySetActivity;
import com.example.androidstudy.adapter.RecyclerAdapter;
import com.example.androidstudy.entity.Exer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class RecyclerFragment extends Fragment {
    private static final String ARG_PARAM = "param";

    private static RecyclerFragment rf;
    private RecyclerView rv;
    private String mParam;
    private Context context;

    private RecyclerFragment.OnFragmentInteractionListener mListener;

    public static RecyclerFragment getRecyclerFragment() {
        if (rf == null){
            rf = new RecyclerFragment();
        }
        return rf;
    }

    public RecyclerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    public static RecyclerFragment newInstance(String param) {
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    private List<Exer> exerList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);

        initData();

        rv = view.findViewById(R.id.list_recycle);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(LayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(exerList);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Exer exer = exerList.get(position);
                Intent intent = new Intent(getContext(), ExerciseDetailActivity.class);
                intent.putExtra("id",exer.getId());  //用于识别是个xml文件
                intent.putExtra("title",exer.getTitle());  //用于设置详情标题栏
                container.getContext().startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return view;
    }

    private void initData() {
        exerList = new ArrayList<>();

        try {
            InputStream input = getResources().getAssets().open("exercise_title.json");

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String str = result.toString(StandardCharsets.UTF_8.name());

            exerList = JSON.parseArray(str,Exer.class);

        } catch (IOException e) {
            e.printStackTrace();
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
