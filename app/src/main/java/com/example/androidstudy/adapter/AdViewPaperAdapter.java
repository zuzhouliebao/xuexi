package com.example.androidstudy.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AdViewPaperAdapter extends PagerAdapter {
    private List<ImageView> imageViews;

    public AdViewPaperAdapter() {
        this(null);
        imageViews = new ArrayList<>();
    }

    public AdViewPaperAdapter(List<ImageView> imageViews) {
        super();
        this.imageViews = imageViews;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //container 容器相当于用来存放imageView

        //从集合中获得图片
        ImageView imageView = imageViews.get(position % imageViews.size());

        //检查imageView是否已经添加到容器中
        ViewParent parent = imageView.getParent();
        if (parent != null){
            ((ViewGroup) parent).removeView(imageView);
        }
        //把图片添加到container中
        container.addView(imageView);
        //把图片返回给框架，用来缓存
        return imageView;
    }

    public int getItemPosition(@NonNull Object o){
        return POSITION_NONE;
    }

    public int getSize(){
        return imageViews.size();
    }
}
