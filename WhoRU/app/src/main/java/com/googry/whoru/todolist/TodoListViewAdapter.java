package com.googry.whoru.todolist;

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
 * Created by SeokJun on 2016-07-15.
 */
public class TodoListViewAdapter extends BaseAdapter {
    private Context context = null;
    private ArrayList<Todo> alTodo = new ArrayList<>();

    public TodoListViewAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return alTodo.size();
    }

    @Override
    public Object getItem(int position) {
        return alTodo.get(position);
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
            convertView = inflater.inflate(R.layout.listitem_todo, null);

            holder.tv_itemDay = (TextView) convertView.findViewById(R.id.tv_itemDay);
            holder.tv_itemContent = (TextView) convertView.findViewById(R.id.tv_itemContent);
            holder.tv_itemJoin = (TextView) convertView.findViewById(R.id.tv_itemJoin);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Todo todo = alTodo.get(position);

        //나중에 이미지 삽입
//        holder.iv_picture.
        holder.tv_itemDay.setText(todo.getDay() + " " + todo.getTime());
        holder.tv_itemContent.setText(todo.getContent());
        holder.tv_itemJoin.setText(todo.getJoin());
        return convertView;
    }

    private class ViewHolder {
        public TextView tv_itemDay, tv_itemContent, tv_itemJoin;

    }

    public void addItem(String day, String time, String content, String join) {
        alTodo.add(new Todo(day, time, content, join));
    }

    public void removeItem(int position) {
        alTodo.remove(position);
    }

    public void removeItem(Todo todo) {
        alTodo.remove(todo);
    }

//    public void sortItem() {
//        Collections.sort(alTodo, Todo.ALPHA_COMPARATOR);
//        this.notifyDataSetChanged();
//    }
}
