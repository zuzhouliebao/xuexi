package com.example.androidstudy.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidstudy.R;
import com.example.androidstudy.entity.PlayH;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<PlayH> playHList;



    static class ViewHolder extends RecyclerView.ViewHolder{
        View historyList;
        TextView historyImg,historyName,historyContent;

        public ViewHolder(View view){
            super(view);
            historyList = view;
            historyImg =  view.findViewById(R.id.history_img);
            historyName =  view.findViewById(R.id.history_name);
            historyContent =  view.findViewById(R.id.history_content);
        }
    }

    public HistoryAdapter(List<PlayH> playHList) {
        this.playHList = playHList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int i) {
        PlayH playH = playHList.get(i);
        holder.historyImg.setText(String.valueOf(i + 1));
//        holder.historyImg.setBackgroundResource(R.mipmap.ic_launcher);
        holder.historyName.setText(playH.getTitle());
        holder.historyContent.setText(playH.getVideoTitle());

        if (itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(holder.itemView,i);
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
        return playHList.size();
    }
}
