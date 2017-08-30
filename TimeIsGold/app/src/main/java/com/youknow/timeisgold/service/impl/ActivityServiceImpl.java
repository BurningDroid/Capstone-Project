package com.youknow.timeisgold.service.impl;

import com.youknow.timeisgold.service.ActivityService;

/**
 * Created by Aaron on 31/08/2017.
 */

public class ActivityServiceImpl implements ActivityService {

    private static ActivityServiceImpl INSTANCE;

    private ActivityServiceImpl() {
    }

    public static ActivityService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityServiceImpl();
        }
        return INSTANCE;
    }
}
