package com.youknow.timeisgold.data.source;

import com.youknow.timeisgold.data.Activity;

import java.util.List;

/**
 * Created by Aaron on 31/08/2017.
 */

public interface ActivityDataSource {

    long createActivity(Activity activity);

    void updateActivity(Activity activity);

    Activity getActivity(long id);

    void deleteActivity(long id);

    Activity getRunningActivity();

    List<Activity> getActivities(long categoryId, long startDate);
}
