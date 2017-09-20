package com.youknow.timeisgold.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by Aaron on 20/09/2017.
 */
public class DateTimeUtilTest {

    long startTime;
    long endTime;

    @Before
    public void setup() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 8, 11, 15, 0, 0);
        startTime = calendar.getTimeInMillis();

        calendar.set(2017, 8, 11, 17, 4, 0);
        endTime = calendar.getTimeInMillis();
    }

    @Test
    public void getElapsedTimeTest() {
        long elapsedTime = endTime - startTime;
        String result = DateTimeUtil.getElapsedTime(elapsedTime);
        assertEquals("2hour 4min 0sec", result);
    }

    @Test
    public void getElapsedTimeTest2() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 8, 11, 15, 4, 0);
        endTime = calendar.getTimeInMillis();

        long elapsedTime = endTime - startTime;
        String result = DateTimeUtil.getElapsedTime(elapsedTime);
        assertEquals("4min 0sec", result);
    }

    @Test
    public void getElapsedTimeShortTest() {
        long elapsedTime = endTime - startTime;
        String result = DateTimeUtil.getElapsedTimeShort(elapsedTime);
        assertEquals("2hour 4min", result);
    }

    @Test
    public void getElapsedTimeShortTest2() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 8, 11, 15, 4, 0);
        endTime = calendar.getTimeInMillis();

        long elapsedTime = endTime - startTime;
        String result = DateTimeUtil.getElapsedTimeShort(elapsedTime);
        assertEquals("4min", result);
    }

    @Test
    public void getElapsedHourTest() {
        long elapsedTime = endTime - startTime;
        float result = DateTimeUtil.getElapsedHour(elapsedTime);
        assertEquals(2.1f, result, 0f);
    }

    @Test
    public void getDateMapTest() {
        assertEquals(0, DateTimeUtil.getDateMap(0).size());
        assertEquals(1, DateTimeUtil.getDateMap(1).size());
        assertEquals(2, DateTimeUtil.getDateMap(2).size());
        assertEquals(3, DateTimeUtil.getDateMap(3).size());
        assertEquals(4, DateTimeUtil.getDateMap(4).size());
    }

    @Test
    public void getTimeTest() {
        assertEquals("15:00 ~ 17:04", DateTimeUtil.getTime(startTime, endTime));
    }

}
