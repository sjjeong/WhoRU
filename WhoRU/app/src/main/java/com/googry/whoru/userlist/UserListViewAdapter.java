package com.googry.whoru.userlist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SeokJun on 2016-07-13.
 */
public class UserListViewAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<User> alUser = new ArrayList<>();

    public UserListViewAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return alUser.size();
    }

    @Override
    public Object getItem(int position) {
        return alUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private class ViewHolder {
        public ImageView iv_picture;
        public TextView tv_itemUser, tv_itemPhone;

    }
}
