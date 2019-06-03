package com.hie2j.bmob_studentlist;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class StuListActivity extends AppCompatActivity implements IOnDelListener{

    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private ArrayList<Student> studentArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_list);

        recyclerView = findViewById(R.id.recycle_list);
        studentAdapter = new StudentAdapter(studentArrayList,StuListActivity.this);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(StuListActivity.this));
        getDataFromBmob();
        studentAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Student s = studentArrayList.get(position);

                Toast.makeText(StuListActivity.this,"姓名"+s.getName()
                        +"年龄"+s.getAge(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StuListActivity.this,EditActivity.class);
                intent.putExtra("NAME",s.getName());
                intent.putExtra("AGE",s.getAge());
                intent.putExtra("PROFESSION",s.getProfession());
                intent.putExtra("SCORE",s.getScore());
                startActivityForResult(intent,1002);
            }
        });
    }

    private void getDataFromBmob() {
        BmobQuery<Student> studentBmobQuery = new BmobQuery<>();
//        studentBmobQuery.addWhereEqualTo("age", 20);
        studentBmobQuery.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if (e == null) {
                    Log.e("Bmob", "size =" + list.size());
                    studentArrayList.addAll(list);
                    studentAdapter.notifyDataSetChanged();
                }else {
                    Log.e("Bmob", e.getMessage());
                }
            }
        });
    }

    @Override
    public void del(int i) {

        Student student = studentArrayList.get(i);

        BmobQuery<Student> studentBmobQuery = new BmobQuery<>();
        studentBmobQuery.addWhereEqualTo("profession",student.getProfession());
        studentBmobQuery.addWhereEqualTo("age",student.getAge());
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

        studentArrayList.remove(i);
        studentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        if (data == null){
            return;
        }
        if (resultCode == 2002){
            final String filePath = "/mnt/shared/Image/" + data.getStringExtra("HEAD");
            final BmobFile headimg = new BmobFile(new File(filePath));
            headimg.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.e("BmobFile", "文件上传成功");
                        String name = data.getStringExtra("NAME");
                        int age = data.getIntExtra("AGE",0);
                        String profession = data.getStringExtra("PROFESSION");
                        int score = data.getIntExtra("SCORE",0);

                        Student student = new Student();
                        student.setName(name);
                        student.setAge(age);
                        student.setScore(score);
                        student.setProfession(profession);
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
                        studentArrayList.add(student);
                        studentAdapter.notifyDataSetChanged();
                    }
                    else{
                        Log.e("BmobFile","文件上传失败");
                    }
                }
            });
        }
        else if (resultCode == 3003){
            String name = data.getStringExtra("NAME");
            int age = data.getIntExtra("AGE",0);
            String profession = data.getStringExtra("PROFESSION");
            int score = data.getIntExtra("SCORE",0);

            for (int i = 0; i < studentArrayList.size(); i++){
                Student student = studentArrayList.get(i);
                if (student.getName().equals(name)){
                    student.setAge(age);
                    student.setProfession(profession);
                    student.setScore(score);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //导入菜单布局
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //创建菜单项的点击事件
        switch (item.getItemId()){
            case R.id.menu_add:
                Intent intent = new Intent(StuListActivity.this,AddActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String s) {
        Toast.makeText(StuListActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
