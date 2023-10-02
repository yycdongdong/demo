package com.randream.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String DateTimeString() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);//"yyyy-MM-dd HH:mm:ss"格式的字符串
        return dateString;
    }
}
