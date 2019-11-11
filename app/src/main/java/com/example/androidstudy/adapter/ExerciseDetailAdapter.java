package com.example.androidstudy.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidstudy.R;
import com.example.androidstudy.entity.SubjectExer;

import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailAdapter extends RecyclerView.Adapter<ExerciseDetailAdapter.ViewHolder> {
    private List<SubjectExer> details;
    private List<String> selectedPos;
    private OnSelectListener onSelectListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subject,tvA,tvB,tvC,tvD;
        ImageView ivA,ivB,ivC,ivD;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            subject = itemView.findViewById(R.id.tv_subject);
            tvA = itemView.findViewById(R.id.tv_a);
            tvB = itemView.findViewById(R.id.tv_b);
            tvC = itemView.findViewById(R.id.tv_c);
            tvD = itemView.findViewById(R.id.tv_d);
            ivA = itemView.findViewById(R.id.iv_a);
            ivB = itemView.findViewById(R.id.iv_b);
            ivC = itemView.findViewById(R.id.iv_c);
            ivD = itemView.findViewById(R.id.iv_d);
        }
    }

    //回调接口，监听A、B、C、D选项的选择
    public interface OnSelectListener{
        void onSelectA(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD);
        void onSelectB(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD);
        void onSelectC(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD);
        void onSelectD(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD);
    }

    //参数：习题的集合，监听器
    public ExerciseDetailAdapter(List<SubjectExer> details,OnSelectListener onSelectListener){
        this.details = details;
        selectedPos = new ArrayList<>();
        this.onSelectListener = onSelectListener;
    }

    @NonNull
    @Override
    public ExerciseDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        //1.获取数据
        SubjectExer exer = details.get(position);

        //2.给控件赋值
        holder.subject.setText(exer.getSubject());
        holder.tvA.setText(exer.getA());
        holder.tvB.setText(exer.getB());
        holder.tvC.setText(exer.getC());
        holder.tvD.setText(exer.getD());

        holder.ivA.setImageResource(R.mipmap.ic_exercise_a);
        holder.ivB.setImageResource(R.mipmap.ic_exercise_b);
        holder.ivC.setImageResource(R.mipmap.ic_exercise_c);
        holder.ivD.setImageResource(R.mipmap.ic_exercise_d);

        //3.设置每一个图标的监听，并处理事件（根据选项判断答案是否正确，显示相应图标）
        holder.ivA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectA(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });

        holder.ivB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectB(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });

        holder.ivC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectC(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });

        holder.ivD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pos = String.valueOf(position);
                if (selectedPos.contains(pos)){
                    selectedPos.remove(pos);
                }else {
                    selectedPos.add(pos);
                }
                onSelectListener.onSelectD(position,holder.ivA,holder.ivB,holder.ivC,holder.ivD);
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    /*public static void setABCDEnable(boolean value,ImageView ivA,ImageView ivB,ImageView ivC,ImageView ivD){
        ivA.setEnabled(value);
        ivB.setEnabled(value);
        ivC.setEnabled(value);
        ivD.setEnabled(value);
    }*/



}
