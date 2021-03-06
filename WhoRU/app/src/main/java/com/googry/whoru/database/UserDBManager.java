package com.googry.whoru.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.googry.whoru.userlist.User;

import java.util.ArrayList;

/**
 * Created by SeokJun on 2016-07-16.
 */
public class UserDBManager extends SQLiteOpenHelper {
    private final static String TABLENAME = "USER_LIST";
    public final static String DBNAME = "User.db";
    public final static int DBVERSER = 1;

    public UserDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLENAME + "(" +
                "name text," +
                "phone text primary key," +
                "email text," +
                "department text," +
                "memo text" +
                ");");

    }

    public void insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "insert into " + TABLENAME +
                " values(" +
                "'" + user.getName() + "', " +
                "'" + user.getPhone() + "', " +
                "'" + user.getEmail() + "', " +
                "'" + user.getDepartment() + "', " +
                "'" + user.getMemo() + "'" +
                ");";
        db.execSQL(query);
    }

//    public void update(User user) {
// dbManager.update("update FOOD_LIST set price = " + price + " where name = '" + name + "';");
//    }

    public void delete(User user) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "delete from " + TABLENAME +
                " where phone = '" + user.getPhone() + "';";
        db.execSQL(query);
    }

    public ArrayList<User> getArrayListData() {
        ArrayList<User> alUser = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLENAME,null);
        while(cursor.moveToNext()){
            User user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),null);
            alUser.add(user);
        }
        return alUser;
    }

    public User getUserToPhone(String phone){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLENAME + " where phone = '"+phone+"'",null);
        User user = null;
        if(cursor.moveToNext()){
            user = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),null);
        }
        return user;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
