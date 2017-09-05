package com.youknow.timeisgold.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Aaron on 06/09/2017.
 */

public class DateFormatUtil {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static String getElapsedTime(long elapsedTime) {

        long hh, mm, ss;

        hh = elapsedTime/1000/60/60;
        mm = (elapsedTime - hh * 60 * 60 * 1000)/1000/60;
        ss = ((elapsedTime - hh * 60 * 60 * 1000) - mm * 60 * 1000)/1000;

        return hh + ":" + mm + ":" + ss;
    }
}
