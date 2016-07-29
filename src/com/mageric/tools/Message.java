package com.mageric.tools;

import java.util.Date;

/**
 * Created by Mageric on 2016/7/29 0029.
 */
public class Message {
    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Message.message = message;
    }

    static String message="";

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Message.user = user;
    }

    static String user;

    public static int getId() {
        return id;
    }

    public static void setId() {
        Message.id ++;
    }

    static int id=0;

    public static Date getTime() {
        return time;
    }

    public static void setTime(Date time) {
        Message.time = time;
    }

    static Date time;
}
