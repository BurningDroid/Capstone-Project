package com.youknow.timeisgold.data.source.local;

import com.youknow.timeisgold.data.source.ActivityDataSource;

/**
 * Created by Aaron on 31/08/2017.
 */

public class LocalActivityDataSource implements ActivityDataSource {

    private static LocalActivityDataSource INSTANCE;

    private LocalActivityDataSource() {

    }

    public static ActivityDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalActivityDataSource();
        }

        return INSTANCE;
    }
}
