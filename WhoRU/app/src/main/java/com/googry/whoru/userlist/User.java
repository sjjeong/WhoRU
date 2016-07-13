package com.googry.whoru.userlist;

import android.graphics.Bitmap;

/**
 * Created by SeokJun on 2016-07-13.
 */
public class User {
    private Bitmap picture;
    private String name, phone;

    public User(String name, String phone, Bitmap picture) {
        this.name = name;
        this.phone = phone;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Bitmap getPicture() {
        return picture;
    }
}
