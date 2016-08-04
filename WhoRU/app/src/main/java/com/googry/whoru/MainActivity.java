package com.googry.whoru;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.googry.whoru.database.TodoDBManager;
import com.googry.whoru.database.UserDBManager;
import com.googry.whoru.todolist.AddTodoActivity;
import com.googry.whoru.todolist.DetailTodoActivity;
import com.googry.whoru.todolist.Todo;
import com.googry.whoru.todolist.TodoListViewAdapter;
import com.googry.whoru.userlist.AddUserActivity;
import com.googry.whoru.userlist.DetailUserActivity;
import com.googry.whoru.userlist.User;
import com.googry.whoru.userlist.UserListViewAdapter;

import java.util.zip.Inflater;


public class MainActivity extends Activity {
    private TextView tv_name1, tv_name2;
    private ViewPager viewPager;
    private Button btn_friend, btn_schedule;
    private ImageButton ibtn_adduser, ibtn_addtodo;

    private UserListViewAdapter userListViewAdapter;
    private TodoListViewAdapter todoListViewAdapter;
    private MyViewPagerAdapter myViewPagerAdapter;

    //SQLite
    private UserDBManager userDBManager;
    private TodoDBManager todoDBManager;

    //intent request code
    private final static int REQUESTCODE_ADDUSER = 1;
    private final static int REQUESTCODE_ADDTODO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();
        setData();

        tv_name1.setText("친구");
        tv_name2.setText(userListViewAdapter.getCount() + "");

        btn_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        ibtn_adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivityForResult(intent, REQUESTCODE_ADDUSER);
            }
        });
        ibtn_addtodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTodoActivity.class);
                startActivityForResult(intent, REQUESTCODE_ADDTODO);

            }
        });

        myViewPagerAdapter = new MyViewPagerAdapter(getApplicationContext());
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener((new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        tv_name1.setText("친구");
                        tv_name2.setText(userListViewAdapter.getCount() + "");
                        ibtn_adduser.setVisibility(View.VISIBLE);
                        ibtn_addtodo.setVisibility(View.GONE);
                    }
                    break;
                    case 1: {
                        tv_name1.setText("일정");
                        tv_name2.setText(todoListViewAdapter.getCount() + "");
                        ibtn_adduser.setVisibility(View.GONE);
                        ibtn_addtodo.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        }));


    }

    private void setLayout() {
        tv_name1 = (TextView) findViewById(R.id.tv_name1);
        tv_name2 = (TextView) findViewById(R.id.tv_name2);
        btn_friend = (Button) findViewById(R.id.btn_friend);
        btn_schedule = (Button) findViewById(R.id.btn_schedule);
        ibtn_adduser = (ImageButton) findViewById(R.id.ibtn_adduser);
        ibtn_addtodo = (ImageButton) findViewById(R.id.ibtn_addtodo);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    private void setData() {
        //get user db
        userListViewAdapter = new UserListViewAdapter(getApplicationContext());
        userDBManager = new UserDBManager(getApplicationContext(), UserDBManager.DBNAME, null, UserDBManager.DBVERSER);
        userListViewAdapter.addItems(userDBManager.getArrayListData());

        //get todo db
        todoListViewAdapter = new TodoListViewAdapter(getApplicationContext());
        todoDBManager = new TodoDBManager(getApplicationContext(), TodoDBManager.DBNAME, null, TodoDBManager.DBVERSER);
        todoListViewAdapter.addItems(todoDBManager.getArrayListData());

    }

    @Override
    protected void onDestroy() {
        userDBManager.close();
        todoDBManager.close();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_ADDUSER: {
                if (resultCode == RESULT_OK) {
                    userListViewAdapter.addItem((User) data.getParcelableExtra("user"));
                    userListViewAdapter.notifyDataSetChanged();
                    tv_name2.setText(userListViewAdapter.getCount() + "");
                    myViewPagerAdapter.notifyDataSetChanged();
                }
            }
            break;
            case REQUESTCODE_ADDTODO: {
                if (resultCode == RESULT_OK) {
                    todoListViewAdapter.addItem((Todo) data.getParcelableExtra("todo"));
                    todoListViewAdapter.notifyDataSetChanged();
                    tv_name2.setText(todoListViewAdapter.getCount() + "");
                    myViewPagerAdapter.notifyDataSetChanged();
                }

            }
            break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public class MyViewPagerAdapter extends PagerAdapter {
        private final static int INT_COUNT = 2;
        private Context context;
        private LayoutInflater mInflater;

        public MyViewPagerAdapter(Context context) {
            super();
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return INT_COUNT;
        }

        //ViewPager에서 사용할 View 객체 생성 및 등록
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = null;
            switch (position) {
                case 0: {
                    v = mInflater.inflate(R.layout.layout_userlist, null);
                    ListView lv_users = (ListView) v.findViewById(R.id.lv_users);
                    lv_users.setAdapter(userListViewAdapter);
                    lv_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), DetailUserActivity.class);
                            intent.putExtra("user", (User) userListViewAdapter.getItem(position));
                            startActivity(intent);
                        }
                    });
                    lv_users.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            userDBManager.delete((User) userListViewAdapter.getItem(position));
                            userListViewAdapter.removeItem(position);
                            userListViewAdapter.notifyDataSetChanged();
                            tv_name2.setText(userListViewAdapter.getCount() + "");
//                            if (userListViewAdapter.getCount() == 0) {
//                                View v = mInflater.inflate(R.layout.layout_userlist, null);
//                                ((LinearLayout) v.findViewById(R.id.ll_userlist)).setVisibility(View.INVISIBLE);
//                                ((LinearLayout) v.findViewById(R.id.ll_usertext)).setVisibility(View.VISIBLE);
//                            }
                            return true;
                        }
                    });

//                    if (userListViewAdapter.getCount() == 0) {
//                        ((LinearLayout) v.findViewById(R.id.ll_userlist)).setVisibility(View.INVISIBLE);
//                        ((LinearLayout) v.findViewById(R.id.ll_usertext)).setVisibility(View.VISIBLE);
//                    } else {
//                        ((LinearLayout) v.findViewById(R.id.ll_userlist)).setVisibility(View.VISIBLE);
//                        ((LinearLayout) v.findViewById(R.id.ll_usertext)).setVisibility(View.INVISIBLE);
//                    }
                }
                break;
                case 1: {
                    v = mInflater.inflate(R.layout.layout_todolist, null);
                    ListView lv_todolist = (ListView) v.findViewById(R.id.lv_todolist);
                    lv_todolist.setAdapter(todoListViewAdapter);
                    lv_todolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), DetailTodoActivity.class);
                            intent.putExtra("todo", (Todo) todoListViewAdapter.getItem(position));
                            startActivity(intent);
                        }
                    });
                    lv_todolist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            todoDBManager.delete((Todo) todoListViewAdapter.getItem(position));
                            todoListViewAdapter.removeItem(position);
                            todoListViewAdapter.notifyDataSetChanged();
                            tv_name2.setText(todoListViewAdapter.getCount() + "");
//                            if (todoListViewAdapter.getCount() == 0) {
//                                View v = mInflater.inflate(R.layout.layout_userlist, null);
//                                ((LinearLayout) v.findViewById(R.id.ll_todolist)).setVisibility(View.INVISIBLE);
//                                ((LinearLayout) v.findViewById(R.id.ll_todotext)).setVisibility(View.VISIBLE);
//                            }
                            return true;
                        }
                    });

//                    if (todoListViewAdapter.getCount() == 0) {
//                        ((LinearLayout) v.findViewById(R.id.ll_todolist)).setVisibility(View.INVISIBLE);
//                        ((LinearLayout) v.findViewById(R.id.ll_todotext)).setVisibility(View.VISIBLE);
//                    } else {
//                        ((LinearLayout) v.findViewById(R.id.ll_todolist)).setVisibility(View.VISIBLE);
//                        ((LinearLayout) v.findViewById(R.id.ll_todotext)).setVisibility(View.INVISIBLE);
//                    }
                }
                break;
            }
            ((ViewPager) viewPager).addView(v, 0);
            return v;
        }

        //View 객체를 삭제
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
