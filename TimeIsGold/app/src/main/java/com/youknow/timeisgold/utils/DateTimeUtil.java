package com.youknow.timeisgold.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Aaron on 06/09/2017.
 */

public class DateTimeUtil {
    private static final String TAG = DateTimeUtil.class.getSimpleName();

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat TIME_FORMAT_SHORT = new SimpleDateFormat("HH:mm");
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public static String getElapsedTime(long elapsedTime) {
        long hh, mm, ss;
        hh = elapsedTime / 1000 / 60 / 60;
        mm = (elapsedTime - hh * 60 * 60 * 1000) / 1000 / 60;
        ss = ((elapsedTime - hh * 60 * 60 * 1000) - mm * 60 * 1000) / 1000;

        if (hh == 0) {
            return mm + " min " + ss + "sec";
        } else {
            return hh + " hour " + mm + " min " + ss + "sec";
        }
    }

    public static String getElapsedTimeShort(long elapsedTime) {
        long hh, mm, ss;
        hh = elapsedTime / 1000 / 60 / 60;
        mm = (elapsedTime - hh * 60 * 60 * 1000) / 1000 / 60;

        if (hh == 0) {
            return mm + " min";
        } else {
            return hh + " hour " + mm + " min";
        }
    }

    public static float getElapsedHour(long elapsedTime) {
        double hh = elapsedTime / 1000 / 60 / 60;
        double mm = ((elapsedTime - hh * 60 * 60 * 1000) / 1000 / 60)/60;

        return Float.parseFloat(DECIMAL_FORMAT.format(hh+mm));
    }

    public static long getBeforeDateTime(int day) {
        day *= -1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        return calendar.getTimeInMillis();
    }

    public static Map<String, Float> getDateMap(int day) {
        day *= -1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, day);
        Map<String, Float> dateMap = new TreeMap<>();
        for (int i = day + 1; i <= 0; i++) {
            calendar.add(Calendar.DATE, 1);
            dateMap.put(DATE_FORMAT.format(new Date(calendar.getTimeInMillis())), 0f);
        }

        Log.d(TAG, "[TIG] getDateMap[" + day + "]: " + dateMap.keySet());
        return dateMap;
    }

    public static String getTime(long startTime, long endTime) {
        return TIME_FORMAT_SHORT.format(new Date(startTime)) + " ~ " + TIME_FORMAT_SHORT.format(new Date(endTime));
    }

}
