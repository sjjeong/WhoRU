package com.googry.whoru;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.googry.whoru.todolist.TodoListViewAdapter;
import com.googry.whoru.userlist.UserListViewAdapter;


public class MainActivity extends AppCompatActivity {
    private Button btn_addUser;
    private TextView tv_name1, tv_name2;
    private ListView lv_users;
    private ViewPager viewPager;

    private Button btn_friend, btn_schedule;

    private UserListViewAdapter userListViewAdapter;
    private TodoListViewAdapter todoListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sample item
        userListViewAdapter = new UserListViewAdapter(getApplicationContext());
        userListViewAdapter.addItem("홍지홍", "010-5555-4444");
        userListViewAdapter.addItem("유혜정", "010-1111-2222");
        userListViewAdapter.addItem("정윤도", "010-3333-6666");
        userListViewAdapter.addItem("진서우", "010-0000-9999");

        //sample item
        todoListViewAdapter = new TodoListViewAdapter(getApplicationContext());
        todoListViewAdapter.addItem("2016-07-12", "14:00", "TW 회의", "정석준, 김진홍, 최재훈");
        todoListViewAdapter.addItem("2016-07-14", "16:00", "TW 개발", "정석준");
        todoListViewAdapter.addItem("2016-07-16", "14:00", "세미나", "정석준, 신동렬, 채윤주, 김얼, 김다연, 박찬호");

        tv_name1 = (TextView) findViewById(R.id.tv_name1);
        tv_name2 = (TextView) findViewById(R.id.tv_name2);

        tv_name1.setText("친구");
        tv_name2.setText(userListViewAdapter.getCount() + "");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyViewPagerAdapter(getApplicationContext()));
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        tv_name1.setText("친구");
                        tv_name2.setText(userListViewAdapter.getCount() + "");
                    }
                    break;
                    case 1: {
                        tv_name1.setText("일정");
                        tv_name2.setText(todoListViewAdapter.getCount() + "");
                    }
                    break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btn_friend = (Button) findViewById(R.id.btn_friend);
        btn_schedule = (Button) findViewById(R.id.btn_schedule);

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

    }

    private class MyViewPagerAdapter extends PagerAdapter {
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

                }
                break;
                case 1: {
                    v = mInflater.inflate(R.layout.layout_todolist, null);
                    ListView lv_todolist = (ListView)v.findViewById(R.id.lv_todolist);
                    lv_todolist.setAdapter(todoListViewAdapter);
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
