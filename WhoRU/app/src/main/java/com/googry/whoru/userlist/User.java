package com.googry.whoru.userlist;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.Collator;
import java.util.Collection;
import java.util.Comparator;

/**
 * Created by SeokJun on 2016-07-13.
 */
public class User implements Parcelable {
    private Bitmap picture;
    private String name, phone, email, department, memo;

    public User(String name, String phone, String email, String department, String memo, Bitmap picture) {
        this.department = department;
        this.email = email;
        this.memo = memo;
        this.name = name;
        this.phone = phone;
        this.picture = picture;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getMemo() {
        return memo;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(department);
        dest.writeString(memo);
    }

    public User(Parcel src) {
        this.name = src.readString();
        this.phone = src.readString();
        this.email = src.readString();
        this.department = src.readString();
        this.memo = src.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
