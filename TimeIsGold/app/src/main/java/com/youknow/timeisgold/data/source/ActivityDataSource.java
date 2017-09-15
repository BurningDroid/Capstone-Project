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

    List<Activity> getActivities(long startDate);

    List<Activity> getActivities(long categoryId, long startDate);

    List<Activity> getAllActivity();

    void deleteActivity(long activityId);

    void deleteActivities();

    Activity getRunningActivity();

}
