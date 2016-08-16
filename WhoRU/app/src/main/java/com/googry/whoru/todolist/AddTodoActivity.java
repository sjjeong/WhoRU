package com.googry.whoru.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.googry.whoru.MainActivity;
import com.googry.whoru.R;
import com.googry.whoru.database.TodoDBManager;
import com.googry.whoru.userlist.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by SeokJun on 2016-07-29.
 */
public class AddTodoActivity extends Activity {
    private EditText et_date, et_time, et_attender, et_content;
    private Button btn_register;

    private TodoDBManager dbManager;

    private int year, month, day, hour, minute;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private ArrayList<User> alUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);
        setLayout();

        dbManager = new TodoDBManager(getApplicationContext(), TodoDBManager.DBNAME, null, TodoDBManager.DBVERSER);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "";
                for (User user : alUser) {
                    str += user.getPhone() + Todo.SPLITUNIT;
                }
                if (str.length() != 0) {
                    str = str.substring(0, str.length() - 1);
                }
                Todo todo = new Todo(et_date.getText().toString(),
                        et_time.getText().toString(),
                        str, et_content.getText().toString());
                dbManager.insert(todo);
                Intent intent = new Intent();
                intent.putExtra("todo", todo);
                setResult(RESULT_OK, intent);
                dbManager.close();
                finish();
            }
        });


        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        String strDate = year + "/" + (month + 1) + "/" + day;
        String strTime = String.format("%02d : %02d", hour, minute);
        et_date.setText(strDate);
        et_time.setText(strTime);

        et_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (datePickerDialog == null) {
                    datePickerDialog = new DatePickerDialog(AddTodoActivity.this, dateSetListener, year, month, day);
                    datePickerDialog.setCanceledOnTouchOutside(true);
                    datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            datePickerDialog = null;
                        }
                    });
                    datePickerDialog.show();
                }
                return false;
            }
        });
        et_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (timePickerDialog == null) {
                    timePickerDialog = new TimePickerDialog(AddTodoActivity.this, timeSetListener, hour, minute, false);
                    timePickerDialog.setCanceledOnTouchOutside(true);
                    timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            timePickerDialog = null;
                        }
                    });
                    timePickerDialog.show();
                }
                return false;
            }
        });
        et_attender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getApplicationContext(), AddUser2TodoActivity.class);
                    startActivityForResult(intent, 0);
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0: {
                switch (resultCode) {
                    case RESULT_OK: {
                        alUser = data.getParcelableArrayListExtra("checkedUsers");
                        String str = "";
                        for (User user : alUser) {
                            str += user.getName() + "/";
                        }
                        if (str.length() != 0) {
                            str = str.substring(0, str.length() - 1);
                        }
                        et_attender.setText(str);
                    }
                    break;
                }
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year_, int monthOfYear, int dayOfMonth) {
//            String msg = String.format("%d / %d / %d", year, monthOfYear + 1, dayOfMonth);
//            Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
            year = year_;
            month = monthOfYear;
            day = dayOfMonth;
            String strDate = year + "/" + (month + 1) + "/" + day;
            et_date.setText(strDate);
            datePickerDialog = null;
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute_) {
//            String msg = String.format("%d / %d / %d", year, hourOfDay, minute);
//            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            hour = hourOfDay;
            minute = minute_;
            String strTime = String.format("%02d : %02d", hour, minute);
            et_time.setText(strTime);
            timePickerDialog = null;
        }
    };

    private void setLayout() {
        et_date = (EditText) findViewById(R.id.et_date);
        et_time = (EditText) findViewById(R.id.et_time);
        et_attender = (EditText) findViewById(R.id.et_attender);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_register = (Button) findViewById(R.id.btn_register);
    }
}
