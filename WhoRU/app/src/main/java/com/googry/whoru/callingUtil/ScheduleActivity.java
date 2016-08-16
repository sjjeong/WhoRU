package com.googry.whoru.callingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.googry.whoru.R;
import com.googry.whoru.database.TodoDBManager;
import com.googry.whoru.database.UserDBManager;
import com.googry.whoru.todolist.DetailTodoActivity;
import com.googry.whoru.todolist.Todo;
import com.googry.whoru.todolist.TodoListViewAdapter;

import java.util.ArrayList;

/**
 * Created by SeokJun on 2016-08-06.
 */
public class ScheduleActivity extends Activity {
    private ListView lv_schedule;
    private String phoneNumber;
    private TodoDBManager todoDBManager;
    private TodoListViewAdapter todoListViewAdapter;
    private UserDBManager userDBManager;

    private ArrayList<Todo> alTodo, alSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        lv_schedule = (ListView) findViewById(R.id.lv_schedule);
        phoneNumber = getIntent().getStringExtra("call_number");
        //get user db
        userDBManager = new UserDBManager(getApplicationContext(), UserDBManager.DBNAME, null, UserDBManager.DBVERSER);
        todoListViewAdapter = new TodoListViewAdapter(getApplicationContext(), userDBManager.getArrayListData());
        todoDBManager = new TodoDBManager(getApplicationContext(), TodoDBManager.DBNAME, null, TodoDBManager.DBVERSER);

        alTodo = todoDBManager.getArrayListData();
        alSchedule = new ArrayList<>();

        for (Todo todo : alTodo) {
            String[] arrStr = todo.getAttender().split(Todo.SPLITUNIT);
            for (String str : arrStr) {
                if (str.equals(phoneNumber)) {
                    alSchedule.add(todo);
                    break;
                }
            }
        }

        todoListViewAdapter.addItems(alSchedule);
        lv_schedule.setAdapter(todoListViewAdapter);
        lv_schedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailTodoActivity.class);
                intent.putExtra("todo", alSchedule.get(i));
                startActivity(intent);
            }
        });
    }
}
