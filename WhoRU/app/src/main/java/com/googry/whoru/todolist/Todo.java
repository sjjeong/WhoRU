package com.googry.whoru.todolist;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SeokJun on 2016-07-15.
 */
public class Todo implements Parcelable {
    private String day, time, attender, content;
    private int id;

    public final static String SPLITUNIT = ":";

    public Todo(String date, String time, String attender, String content) {
        this.day = date;
        this.time = time;
        this.attender = attender;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDay() {
        return day;
    }

    public String getAttender() {
        return attender;
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeString(time);
        dest.writeString(attender);
        dest.writeString(content);
    }

    public Todo(Parcel src) {
        this.day = src.readString();
        this.time = src.readString();
        this.attender = src.readString();
        this.content = src.readString();
    }

    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {

        @Override
        public Todo createFromParcel(Parcel source) {
            return new Todo(source);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    //이름으로 정렬
//    public static final Comparator<Todo> ALPHA_COMPARATOR = new Comparator<Todo>() {
//        private final Collator sCollator = Collator.getInstance();
//
//        @Override
//        public int compare(Todo lhs, Todo rhs) {
//            return sCollator.compare(lhs.getName(), rhs.getName());
//        }
//
//    };
}
