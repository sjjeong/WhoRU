package com.googry.whoru.userlist;

import android.graphics.Bitmap;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;

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

    //이름으로 정렬
    public static final Comparator<User> ALPHA_COMPARATOR = new Comparator<User>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(User lhs, User rhs) {
            return sCollator.compare(lhs.getName(), rhs.getName());
        }

    };
}
