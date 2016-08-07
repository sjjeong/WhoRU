package com.googry.whoru.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.googry.whoru.callingUtil.Memo;
import com.googry.whoru.todolist.Todo;

import java.util.ArrayList;

/**
 * Created by SeokJun on 2016-08-06.
 */
public class MemoDBManager extends SQLiteOpenHelper {
    private final static String TABLENAME = "MEMO";
    public final static String DBNAME = "Memo.db";
    public final static int DBVERSER = 1;

    public MemoDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME + "(" +
                "_id integer primary key autoincrement, " +
                "phone text," +
                "day text, " +
                "time text, " +
                "content text" +
                ");");
    }

    public void insert(Memo memo) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "insert into " + TABLENAME +
                "(phone, day, time, content) values(" +
                "'" + memo.getPhone() + "', " +
                "'" + memo.getDay() + "', " +
                "'" + memo.getTime() + "', " +
                "'" + memo.getContent() + "'" +
                ");";
        db.execSQL(query);
    }

//    public void update(User user) {
// dbManager.update("update FOOD_LIST set price = " + price + " where name = '" + name + "';");
//    }

    public void delete(Memo memo) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from " + TABLENAME +
                " where phone = " + memo.getPhone() + ";";
        db.execSQL(query);
    }

    public ArrayList<Memo> getArrayListData() {
        ArrayList<Memo> alMemo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLENAME, null);
        while (cursor.moveToNext()) {;
            Memo memo = new Memo(cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
            memo.setId(cursor.getInt(0));
            alMemo.add(memo);
        }
        return alMemo;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
