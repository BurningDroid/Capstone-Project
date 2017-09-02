package com.youknow.timeisgold.service.impl;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.service.ActivityService;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Aaron on 02/09/2017.
 */
public class ActivityServiceImplTest {

    ActivityService activityService;

    @Before
    public void setup() {
        activityService = Injection.provideActivityService();
    }

    @Test
    public void ctorTest() {
        assertNotNull(activityService);
    }
}