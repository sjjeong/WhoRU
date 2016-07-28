package com.googry.whoru;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.googry.whoru.userlist.User;

/**
 * Created by SeokJun on 2016-07-28.
 */
public class DetailUserActivity extends Activity {
    private ImageView iv_profile;
    private EditText et_name, et_phone, et_email, et_department, et_memo;
    private Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailuser);
        setLayout();
        User user = getIntent().getParcelableExtra("user");
        setData(user);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setData(User user) {
        et_name.setText(user.getName());
        et_phone.setText(user.getPhone());
        et_email.setText(user.getEmail());
        et_department.setText(user.getDepartment());
        et_memo.setText(user.getMemo());
    }

    private void setLayout() {
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_email = (EditText) findViewById(R.id.et_email);
        et_department = (EditText) findViewById(R.id.et_department);
        et_memo = (EditText) findViewById(R.id.et_memo);
        btn_ok = (Button) findViewById(R.id.btn_ok);
    }
}
