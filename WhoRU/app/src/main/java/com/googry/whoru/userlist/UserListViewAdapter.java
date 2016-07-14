package com.googry.whoru.userlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.googry.whoru.R;

import java.util.ArrayList;
import java.util.Collections;

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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.layout_userlist, null);

            holder.iv_picture = (ImageView) convertView.findViewById(R.id.iv_picture);
            holder.tv_itemName = (TextView) convertView.findViewById(R.id.tv_itemName);
            holder.tv_itemPhone = (TextView) convertView.findViewById(R.id.tv_itemPhone);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = alUser.get(position);

        //나중에 이미지 삽입
//        holder.iv_picture.
        holder.tv_itemName.setText(user.getName());
        holder.tv_itemPhone.setText(user.getPhone());
        return null;
    }

    private class ViewHolder {
        public ImageView iv_picture;
        public TextView tv_itemName, tv_itemPhone;

    }

    public void addItem(String name, String phone) {
        alUser.add(new User(name, phone, null));
    }

    public void removeItem(int position) {
        alUser.remove(position);
    }

    public void removeItem(User user) {
        alUser.remove(user);
    }

    public void sortItem() {
        Collections.sort(alUser, User.ALPHA_COMPARATOR);
        this.notifyDataSetChanged();
    }
}
