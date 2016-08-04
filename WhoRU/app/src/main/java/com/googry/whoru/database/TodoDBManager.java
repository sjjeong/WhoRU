package com.googry.whoru.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.googry.whoru.todolist.Todo;

import java.util.ArrayList;

/**
 * Created by SeokJun on 2016-07-16.
 */
public class TodoDBManager extends SQLiteOpenHelper {
    private final static String TABLENAME = "TODO_LIST";
    public final static String DBNAME = "Todo.db";
    public final static int DBVERSER = 1;

    public TodoDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME + "(" +
                "_id integer primary key autoincrement, " +
                "day text, " +
                "time text, " +
                "attender text, " +
                "content text" +
                ");");

    }

    public void insert(Todo todo) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "insert into " + TABLENAME +
                "(day, time, attender, content) values(" +
                "'" + todo.getDay() + "', " +
                "'" + todo.getTime() + "', " +
                "'" + todo.getAttender() + "', " +
                "'" + todo.getContent() + "'" +
                ");";
//        String query = "insert into " + TABLENAME +
//                " values(null, " +
//                "'" + todo.getDay() + "', " +
//                "'" + todo.getTime() + "', " +
//                "'" + todo.getAttender() + "', " +
//                "'" + todo.getContent() + "'" +
//                ");";
        db.execSQL(query);
    }

//    public void update(User user) {
// dbManager.update("update FOOD_LIST set price = " + price + " where name = '" + name + "';");
//    }

    public void delete(Todo todo) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from " + TABLENAME +
                " where _id = " + todo.getId() + ";";
        db.execSQL(query);
    }

    public ArrayList<Todo> getArrayListData() {
        ArrayList<Todo> alTodo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLENAME,null);
        while(cursor.moveToNext()){
            Todo user = new Todo(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            user.setId(cursor.getInt(0));
            alTodo.add(user);
        }
        return alTodo;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
