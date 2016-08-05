package com.googry.whoru.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.googry.whoru.R;
import com.googry.whoru.database.UserDBManager;
import com.googry.whoru.userlist.User;
import com.googry.whoru.userlist.UserListViewAdapter;

import java.util.ArrayList;

public class AddUser2TodoActivity extends AppCompatActivity {
    private ListView lv_users;
    private Button btn_ok;

    private UserListViewAdapter userListViewAdapter;
    private UserDBManager userDBManager;

    private int[] arrCheckUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser2todo);
        setLayout();

        //get user db
        userListViewAdapter = new UserListViewAdapter(getApplicationContext());
        userDBManager = new UserDBManager(getApplicationContext(), UserDBManager.DBNAME, null, UserDBManager.DBVERSER);
        userListViewAdapter.addItems(userDBManager.getArrayListData());
        arrCheckUser = new int[userListViewAdapter.getCount()];
        lv_users.setAdapter(userListViewAdapter);
        lv_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout ll_useritem = (LinearLayout) view.findViewById(R.id.ll_useritem);
                if(arrCheckUser[i] == 1){
                    ll_useritem.setBackgroundColor(Color.WHITE);
                    arrCheckUser[i] = 0;
                }else{
                    ll_useritem.setBackgroundColor(Color.GRAY);
                    arrCheckUser[i] = 1;
                }
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<User> alUsers = new ArrayList<User>();
                int loop = arrCheckUser.length;
                for(int i=0;i<loop;i++){
                    if(arrCheckUser[i]==1){
                        alUsers.add((User) userListViewAdapter.getItem(i));
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("checkedUsers",alUsers);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    private void setLayout() {
        lv_users = (ListView) findViewById(R.id.lv_users);
        btn_ok = (Button) findViewById(R.id.btn_ok);
    }
}
