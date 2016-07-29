package com.googry.whoru.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.googry.whoru.R;
import com.googry.whoru.database.TodoDBManager;

/**
 * Created by SeokJun on 2016-07-29.
 */
public class AddTodoActivity extends Activity {
    private EditText et_date, et_time, et_attender, et_content;
    private Button btn_register;

    private TodoDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);
        setLayout();

        dbManager = new TodoDBManager(getApplicationContext(), TodoDBManager.DBNAME, null, TodoDBManager.DBVERSER);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = new Todo(et_date.getText().toString(),
                        et_time.getText().toString(),
                        et_attender.getText().toString(),
                        et_content.getText().toString());
                dbManager.insert(todo);
                Intent intent = new Intent();
                intent.putExtra("user",todo);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void setLayout() {
        et_date = (EditText) findViewById(R.id.et_date);
        et_time = (EditText) findViewById(R.id.et_time);
        et_attender = (EditText) findViewById(R.id.et_attender);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_register = (Button) findViewById(R.id.btn_register);
    }
}
