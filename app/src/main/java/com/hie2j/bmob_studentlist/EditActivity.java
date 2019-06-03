package com.hie2j.bmob_studentlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private TextView tv_name;
    private EditText et_age;
    private EditText et_profession;
    private EditText et_score;

    private Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("编辑学生");

        initFindViews();
        initElements();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tv_name.getText().toString();
                int age = Integer.parseInt(et_age.getText().toString());
                String profession = et_profession.getText().toString();
                int score = Integer.parseInt(et_score.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("NAME",name);
                intent.putExtra("AGE",age);
                intent.putExtra("PROFESSION",profession);
                intent.putExtra("SCORE",score);
                setResult(3003,intent);

                finish();
            }
        });

    }

    private void initElements() {
        Intent intent = getIntent();
        tv_name.setText(intent.getStringExtra("NAME"));
        et_age.setText(String.valueOf(intent.getIntExtra("AGE",0)));
        et_profession.setText(intent.getStringExtra("PROFESSION"));
        et_score.setText(String.valueOf(intent.getIntExtra("SCORE",0)));
    }

    private void initFindViews() {
        tv_name = findViewById(R.id.tv_name);
        et_age = findViewById(R.id.et_age);
        et_profession = findViewById(R.id.et_profession);
        et_score = findViewById(R.id.et_score);
        btn_save = findViewById(R.id.btn_save);
    }
}
