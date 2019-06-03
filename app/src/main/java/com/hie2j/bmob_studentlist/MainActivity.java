package com.hie2j.bmob_studentlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity {

    private Button btn_add;
    private Button btn_del;
    private Button btn_query;
    private Button btn_list;
    private ImageView iv_head;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFindViews();
    }

    private void initFindViews() {
        btn_add = findViewById(R.id.btn_add);
        btn_query = findViewById(R.id.btn_query);
        btn_del = findViewById(R.id.btn_del);
        btn_list = findViewById(R.id.btn_list);
        iv_head = findViewById(R.id.iv_head);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StuListActivity.class);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = "/mnt/shared/Image/head.jpg";
                final BmobFile headimg = new BmobFile(new File(filePath));
                headimg.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("BmobFile", "文件上传成功");
                            Student student = new Student();
                            student.setName("罗安鹏1");
                            student.setAge(20);
                            student.setProfession("软件技术");
                            student.setScore(100);
                            student.setHeadimg(headimg);
                            student.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        toast("保存成功" + s);
                                    }else{
                                        toast("保存失败" + s);
                                    }
                                }
                            });
                        }
                        else{
                            Log.e("BmobFile","文件上传失败");
                        }
                    }
                });
            }
        });
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Student> studentBmobQuery = new BmobQuery<>();
                studentBmobQuery.addWhereEqualTo("profession","软件技术");
                studentBmobQuery.addWhereEqualTo("age",20);
                studentBmobQuery.addWhereEqualTo("name","罗安鹏1");
                studentBmobQuery.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> studentList, BmobException e) {
                        if (e == null) {
                            StringBuffer stringBuffer = new StringBuffer();
                            for (Student student : studentList){
                                stringBuffer.append(student).append("\n");
                                BmobFile headimg = student.getHeadimg();
                                Log.e("BmobFile","fileUrl : " + headimg.getFileUrl());
                                Log.e("BmobFile","Url : " + headimg.getUrl());
                                Glide.with(MainActivity.this).load(headimg.getUrl()).into(iv_head);
                            }
                        }
                    }
                });
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<Student> studentBmobQuery = new BmobQuery<>();
                studentBmobQuery.addWhereEqualTo("profession","软件技术");
                studentBmobQuery.addWhereEqualTo("age",20);
                studentBmobQuery.findObjects(new FindListener<Student>() {
                    @Override
                    public void done(List<Student> studentList, BmobException e) {
                        if (e == null) {
                            for (Student student : studentList){
                                student.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e == null){
                                            toast("删除成功 " );
                                        }else{
                                            toast("删除失败 " + e.getMessage());
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }

    private void toast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
