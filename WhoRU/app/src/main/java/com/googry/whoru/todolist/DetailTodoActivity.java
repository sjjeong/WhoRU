package com.googry.whoru.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.googry.whoru.R;

public class DetailTodoActivity extends AppCompatActivity {
    private EditText et_date, et_time, et_attender, et_content;
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailtodo);
        setLayout();
        Todo todo = getIntent().getParcelableExtra("todo");
        setData(todo,getIntent());


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setData(Todo todo,Intent intent) {
        et_date.setText(todo.getDay());
        et_time.setText(todo.getTime());
        et_attender.setText(intent.getStringExtra("attender"));
        et_content.setText(todo.getContent());
    }

    private void setLayout() {
        et_date = (EditText) findViewById(R.id.et_date);
        et_time = (EditText) findViewById(R.id.et_time);
        et_attender = (EditText) findViewById(R.id.et_attender);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_ok = (Button) findViewById(R.id.btn_ok);
    }
}
