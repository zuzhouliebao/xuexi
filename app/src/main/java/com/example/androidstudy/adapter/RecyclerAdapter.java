package com.example.androidstudy.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidstudy.R;
import com.example.androidstudy.entity.Exer;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Exer> mexerList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View exerciseView;
        TextView listImg,listName,listContent;

        public ViewHolder(View view){
            super(view);
            exerciseView = view;
            listImg = (TextView) view.findViewById(R.id.list_img);
            listName = (TextView) view.findViewById(R.id.list_name);
            listContent = (TextView) view.findViewById(R.id.list_content);
        }
    }

    public RecyclerAdapter(List<Exer> exerList){
        mexerList = exerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        /*holder.exerciseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Exer exer = mexerList.get(position);
                Toast.makeText(view.getContext(), "click" + exer.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });*/
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Exer exer = mexerList.get(position);
        holder.listImg.setText(String.valueOf(position + 1));
//        holder.listImg.setBackgroundResource(R.mipmap.ic_launcher);
        holder.listName.setText(exer.getTitle());
        holder.listContent.setText(exer.getSubTitle());

        // 设置圆角背景的颜色
        GradientDrawable drawable = (GradientDrawable) holder.listImg.getBackground();
        drawable.setColor(Color.parseColor(exer.getBgColor()));

        if (itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mexerList.size();
    }
}
