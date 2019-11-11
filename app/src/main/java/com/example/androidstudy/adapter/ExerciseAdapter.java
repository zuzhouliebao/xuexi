package com.example.androidstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidstudy.R;
import com.example.androidstudy.entity.Exer;

import java.util.List;


public class ExerciseAdapter extends BaseAdapter {
    private List<Exer> exercises;
    private Context context;

    public ExerciseAdapter(Context context,List<Exer> exercises){
        this.context = context;
        this.exercises = exercises;

    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Exer getItem(int position) {
        return exercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        //1.加载item_list.xml布局，只需要加载一次
        if (convertView == null){
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_title,null);

            //2.获取控件对象
            hodler.listName = convertView.findViewById(R.id.list_name);
            hodler.listContent = convertView.findViewById(R.id.list_content);
            hodler.listImg = convertView.findViewById(R.id.list_img);

            convertView.setTag(hodler);
        }else {
            hodler = (ViewHodler) convertView.getTag();
        }
        //3.加载数据
        final Exer exercise = getItem(position);
        if (exercise != null){
            hodler.listImg.setText(String.valueOf(position + 1));
            hodler.listImg.setBackgroundResource(R.mipmap.ic_launcher);
            hodler.listName.setText(exercise.getTitle());
            hodler.listContent.setText(exercise.getSubTitle());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return convertView;
    }

    static class ViewHodler{
        TextView listImg,listName,listContent;
    }
}
