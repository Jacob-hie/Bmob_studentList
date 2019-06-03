package com.hie2j.bmob_studentlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_age;
    private EditText et_profession;
    private EditText et_score;
    private EditText et_head;

    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("添加学生");

        initFindViews();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                int age = Integer.parseInt(et_age.getText().toString());
                String profession = et_profession.getText().toString();
                int score = Integer.parseInt(et_score.getText().toString());
                String head = et_head.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("NAME",name);
                intent.putExtra("AGE",age);
                intent.putExtra("PROFESSION",profession);
                intent.putExtra("SCORE",score);
                intent.putExtra("HEAD",head);
                setResult(2002,intent);

                finish();
            }
        });

    }

    private void initFindViews() {
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_profession = findViewById(R.id.et_profession);
        et_score = findViewById(R.id.et_score);
        et_head = findViewById(R.id.et_head);
        btn_save = findViewById(R.id.btn_save);
    }
}
