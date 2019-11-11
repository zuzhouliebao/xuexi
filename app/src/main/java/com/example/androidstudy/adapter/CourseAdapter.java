package com.example.androidstudy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidstudy.R;
import com.example.androidstudy.entity.Course;

import java.util.ArrayList;
import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private List<Course> courses;
    private List<Integer> imgIds;

    public CourseAdapter(List<Course> courses) {
        this.courses = courses;
        setImgIds();
    }

    private void setImgIds() {
        imgIds = new ArrayList<>();
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);
        imgIds.add(R.drawable.avatar);

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImg;
        TextView tvImgTitle;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvImgTitle = itemView.findViewById(R.id.tv_img_title);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Course course = courses.get(i);
        viewHolder.ivImg.setImageResource(imgIds.get(i));
        viewHolder.tvTitle.setText(course.getTitle());
        viewHolder.tvImgTitle.setText(course.getImgTitle());

        if (onItemClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(viewHolder.itemView,i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
               void  onItemClick(View view,int position);
    }
}
