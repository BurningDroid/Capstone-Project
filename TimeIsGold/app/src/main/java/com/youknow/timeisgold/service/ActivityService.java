package com.youknow.timeisgold.service;

import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.History;

import java.util.List;

/**
 * Created by Aaron on 31/08/2017.
 */

public interface ActivityService {
    long createActivity(Activity activity);

    void updateActivity(Activity activity);

    void getRunningActivity(OnLoadedActivityListener callback);

    void getActivities(long categoryId, long startDate, OnLoadedActivitiesListener callback);

    void getAllActivity(OnLoadedActivitiesListener callback);

    void deleteActivities();

    void getAllHistory(OnLoadedHistoriesListener callback);

    interface OnLoadedActivityListener {
        void onLoadedActivity(Activity activity);
    }

    interface OnLoadedActivitiesListener {
        void onLoadedActivities(List<Activity> activities);
    }

    interface OnLoadedHistoriesListener {
        void onLoadedHistories(List<History> histories);
    }
}
