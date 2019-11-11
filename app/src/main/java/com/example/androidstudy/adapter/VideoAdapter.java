package com.example.androidstudy.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.androidstudy.R;
import com.example.androidstudy.entity.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<Video> videos;

    private int selected;

    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
        this.selected = -1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivPlay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivPlay = itemView.findViewById(R.id.iv_play);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_media, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int position) {
        Video video = videos.get(position);
        viewHolder.ivPlay.setImageResource(R.mipmap.ic_play);
        viewHolder.tvTitle.setText(video.getVideoTitle());

        // 改变选中项的图标和文本颜色
        if(selected == position) {
            viewHolder.ivPlay.setImageResource(R.mipmap.ic_video_play);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#009958"));
        } else {
            viewHolder.ivPlay.setImageResource(R.mipmap.ic_play);
            viewHolder.tvTitle.setTextColor(Color.parseColor("#333333"));
//            viewHolder.tvTitle.setTextColor(context.getResources().getColor(R.color.cardview_dark_background));
        }

        if (itemClickListener != null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(viewHolder.itemView,position);
                }
            });
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
