package com.hie2j.bmob_studentlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private ArrayList<Student> studentArrayList;
    private IOnDelListener delListener;

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public StudentAdapter(ArrayList<Student> studentArrayList,IOnDelListener delListener) {
        this.studentArrayList = studentArrayList;
        this.delListener = delListener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.list_item,viewGroup,false
        );
        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder studentViewHolder, final int i) {
        Student student = studentArrayList.get(i);
        studentViewHolder.tv_name.setText(student.getName());
        studentViewHolder.tv_age.setText(String.valueOf(student.getAge()));
        Glide.with(studentViewHolder.iv_head.getContext()).load(student.getHeadimg().getUrl()).into(studentViewHolder.iv_head);
        studentViewHolder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delListener.del(i);
            }
        });
        studentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }
}
