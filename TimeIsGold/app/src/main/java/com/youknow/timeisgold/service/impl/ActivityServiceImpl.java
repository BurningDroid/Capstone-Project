package com.youknow.timeisgold.service.impl;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.data.source.firebase.FirebaseActivityDataSource;
import com.youknow.timeisgold.service.ActivityService;
import com.youknow.timeisgold.service.UserService;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 31/08/2017.
 */

public class ActivityServiceImpl implements ActivityService {

    private static final String TAG = ActivityServiceImpl.class.getSimpleName();

    private static ActivityServiceImpl INSTANCE;

    private UserService mUserService;
    private ActivityDataSource mLocalActivityDataSource;
    private CategoryDataSource mCategoryDataSource;
    private FirebaseActivityDataSource mRemoteActivityDataSource;

    private ActivityServiceImpl(Context context) {
        mUserService = Injection.provideUserService(context);
        mLocalActivityDataSource = Injection.provideLocalActivityDataSource(context);
        mCategoryDataSource = Injection.provideCategoryDataSource(context);
        mRemoteActivityDataSource = Injection.provideRemoteActivityDataSource();
    }

    public static ActivityService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ActivityServiceImpl(context);
        }

        return INSTANCE;
    }

    @Override
    public long createActivity(final Activity activity) {
        long activityId = mLocalActivityDataSource.createActivity(activity);
        activity.setId(activityId);

        if (mUserService.isAuthenticated()) {
            String userId = mUserService.getUserId();
            mRemoteActivityDataSource.saveActivity(userId, activity);
        }

        return activityId;
    }

    @Override
    public void updateActivity(Activity activity) {
        mLocalActivityDataSource.updateActivity(activity);

        if (mUserService.isAuthenticated()) {
            String userId = mUserService.getUserId();
            mRemoteActivityDataSource.saveActivity(userId, activity);
        }
    }

    @Override
    public void getActivity(long activityId, OnLoadedActivityListener callback) {
        callback.onLoadedActivity(mLocalActivityDataSource.getActivity(activityId));
    }

    @Override
    public void getActivities(long startDate, OnLoadedActivitiesListener callback) {
        callback.onLoadedActivities(mLocalActivityDataSource.getActivities(startDate));
    }

    @Override
    public void getRunningActivity(OnLoadedActivityListener callback) {
        callback.onLoadedActivity(mLocalActivityDataSource.getRunningActivity());
    }

    @Override
    public void getActivities(long categoryId, long startDate, OnLoadedActivitiesListener callback) {
        callback.onLoadedActivities(mLocalActivityDataSource.getActivities(categoryId, startDate));
    }

    @Override
    public void getAllActivity(OnLoadedActivitiesListener callback) {
        callback.onLoadedActivities(mLocalActivityDataSource.getAllActivity());
    }

    @Override
    public void deleteActivity(long activityId) {
        mLocalActivityDataSource.deleteActivity(activityId);

        if (mUserService.isAuthenticated()) {
            String userId = mUserService.getUserId();
            mRemoteActivityDataSource.deleteActivity(userId, activityId);
        }
    }

    @Override
    public void deleteActivities() {
        mLocalActivityDataSource.deleteActivities();

        if (mUserService.isAuthenticated()) {
            String userId = mUserService.getUserId();
            mRemoteActivityDataSource.deleteActivities(userId);
        }
    }

    @Override
    public void getAllHistory(final OnLoadedHistoriesListener callback) {
        mLocalActivityDataSource.getAllHistory(new OnLoadedHistoriesListener(){

            @Override
            public void onLoadedHistories(List<History> histories) {
                callback.onLoadedHistories(histories);
                Log.d(TAG, "[TIG] getAllHistory - size: " + histories.size());
            }
        });
    }

    @Override
    public void getHistories(long startDate, final OnLoadedHistoriesListener callback) {
        getActivities(startDate, new OnLoadedActivitiesListener() {
            @Override
            public void onLoadedActivities(List<Activity> activities) {
                List<History> histories = convertToHistory(activities);
                Log.d(TAG, "[TIG] getHistories - size: " + histories.size());
                callback.onLoadedHistories(histories);
            }
        });
    }

    private List<History> convertToHistory(List<Activity> activities) {
        List<History> histories = new ArrayList<>();

        for (Activity activity : activities) {
            Category category = mCategoryDataSource.getCategory(activity.getCategoryId());
            History history = new History(activity);
            history.setCategory(category);
            histories.add(history);
        }

        return histories;
    }

}
