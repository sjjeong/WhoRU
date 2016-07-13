package com.googry.whoru;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button btn_addUser;
    private ListView lv_users;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyViewPagerAdapter(getApplicationContext()));
        viewPager.setCurrentItem(0);

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
            Toast.makeText(context, position + "번", Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0: {
                    v = mInflater.inflate(R.layout.layout_userlist, null);
                    ListView lv_users = (ListView)v.findViewById(R.id.lv_users);

                }
                break;
                case 1: {
                    v = mInflater.inflate(R.layout.layout_todolist, null);
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
