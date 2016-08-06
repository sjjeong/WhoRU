package com.googry.whoru.callingUtil;

/**
 * Created by SeokJun on 2016-08-06.
 */
public class Memo {
    private String phone, day, time, content;

    public Memo(String phone, String day, String time, String content) {
        this.day = day;
        this.content = content;
        this.phone = phone;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public String getContent() {
        return content;
    }

    public String getPhone() {
        return phone;
    }

    public String getTime() {
        return time;
    }
}
