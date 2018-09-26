package com.citytuike.util;

public class Mobile {
    private static int code=1;
    private static String message="请求成功";

    public static int getCode() {
        return code;
    }

    public static void setCode(int code) {
        Mobile.code = code;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Mobile.message = message;
    }

    public static String getTaskid() {
        return taskid;
    }

    public static void setTaskid(String taskid) {
        Mobile.taskid = taskid;
    }

    private static String taskid="";
}
