package com.googry.whoru.todolist;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by SeokJun on 2016-07-15.
 */
public class Todo {
    private String day, time, content, join;

    public Todo(String day, String time, String content, String join) {
        this.content = content;
        this.day = day;
        this.join = join;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getDay() {
        return day;
    }

    public String getJoin() {
        return join;
    }

    public String getTime() {
        return time;
    }

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
