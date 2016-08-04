package com.googry.whoru.userlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.googry.whoru.R;
import com.googry.whoru.database.UserDBManager;
import com.googry.whoru.userlist.User;

/**
 * Created by SeokJun on 2016-07-16.
 */

public class AddUserActivity extends Activity {
    private ImageView iv_profile;
    private EditText et_name, et_phone, et_email, et_department, et_memo;
    private Button btn_register;

    private UserDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        setLayout();

        dbManager = new UserDBManager(getApplicationContext(), UserDBManager.DBNAME, null, UserDBManager.DBVERSER);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(et_name.getText().toString(),
                        et_phone.getText().toString(),
                        et_email.getText().toString(),
                        et_department.getText().toString(),
                        et_memo.getText().toString(),
                        null);
                dbManager.insert(user);
                Intent intent = new Intent();
                intent.putExtra("user",user);
                setResult(RESULT_OK,intent);
                dbManager.close();
                finish();
            }
        });
    }

    private void setLayout() {
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        et_department = (EditText) findViewById(R.id.et_department);
        et_memo = (EditText) findViewById(R.id.et_memo);
        btn_register = (Button) findViewById(R.id.btn_register);
    }
}
