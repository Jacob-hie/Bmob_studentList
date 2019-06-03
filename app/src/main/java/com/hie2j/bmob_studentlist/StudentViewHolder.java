package com.hie2j.bmob_studentlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StudentViewHolder extends RecyclerView.ViewHolder{

    ImageView iv_head;
    TextView tv_name;
    TextView tv_age;
    ImageView iv_del;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        iv_head = itemView.findViewById(R.id.iv_head);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_age = itemView.findViewById(R.id.tv_age);
        iv_del = itemView.findViewById(R.id.iv_del);
    }
}
