package com.youknow.timeisgold.service.impl;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.User;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.data.source.UserDataSource;
import com.youknow.timeisgold.data.source.firebase.FirebaseActivityDataSource;
import com.youknow.timeisgold.data.source.firebase.FirebaseCategoryDataSource;
import com.youknow.timeisgold.service.ActivityService;
import com.youknow.timeisgold.service.CategoryService;
import com.youknow.timeisgold.service.UserService;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by Aaron on 16/09/2017.
 */

public class DataSyncTask extends AsyncTask<User, Void, Void> {

    private static final String TAG = DataSyncTask.class.getSimpleName();

    private UserService.AuthListner mCallback;
    private UserDataSource mUserDataSource;
    private CategoryDataSource mLocalCategoryDataSource;
    private FirebaseCategoryDataSource mRemoteCategoryDataSource;
    private ActivityDataSource mLocalActivityDataSource;
    private FirebaseActivityDataSource mRemoteActivityDataSource;

    public DataSyncTask(Context context, UserService.AuthListner callback) {
        mCallback = callback;
        mUserDataSource = Injection.provideUserDataSource();
        mLocalCategoryDataSource = Injection.provideCategoryDataSource(context);
        mRemoteCategoryDataSource = Injection.provideRemoteCategoryDataSource();
        mLocalActivityDataSource = Injection.provideLocalActivityDataSource(context);
        mRemoteActivityDataSource = Injection.provideRemoteActivityDataSource();
    }

    @Override
    protected Void doInBackground(User... params) {
        User user = params[0];
        User savedUser = params[1];

        syncCategoryFromLocalToRemote(user.getId());
        syncActivityFromLocalToRemote(user.getId());

        if (savedUser == null) {
            Log.d(TAG, "[TIG] authenticate - Create a New User: " + user);
            mUserDataSource.saveUser(user);
        } else {
            Log.d(TAG, "[TIG] authenticate - This user already exist: " + user);
            syncCategoryFromRemoteToLocal(user.getId());
            syncActivityFromRemoteToLocal(user.getId());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mCallback.done();
    }

    private void syncCategoryFromLocalToRemote(String userId) {
        List<Category> categories = mLocalCategoryDataSource.getAllCategory();
        Log.d(TAG, "[TIG] syncCategoryFromLocalToRemote - " + userId + ", size: " + categories.size());
        for (Category category : categories) {
            mRemoteCategoryDataSource.saveCategory(userId, category);
        }
    }

    private void syncActivityFromLocalToRemote(final String userId) {
        List<Activity> activities = mLocalActivityDataSource.getAllActivity();
        Log.d(TAG, "[TIG] syncActivityFromLocalToRemote - " + userId + ", size: " + activities.size());
        for (Activity activity : activities) {
            mRemoteActivityDataSource.saveActivity(userId, activity);
        }
    }

    private void syncCategoryFromRemoteToLocal(final String userId) {
        mRemoteCategoryDataSource.getAllCategory(userId, new CategoryService.OnLoadedCategoriesListener() {
            @Override
            public void onLoadedCategories(List<Category> categories) {
                Log.d(TAG, "[TIG] syncCategoryFromRemoteToLocal - " + userId + ", size: " + categories.size());
                for (Category category : categories) {
                    mLocalCategoryDataSource.createCategory(category);
                }
            }
        });
    }

    private void syncActivityFromRemoteToLocal(final String userId) {
        mRemoteActivityDataSource.getAllActivity(userId, new ActivityService.OnLoadedActivitiesListener() {
            @Override
            public void onLoadedActivities(List<Activity> activities) {
                Log.d(TAG, "[TIG] syncActivityFromRemoteToLocal - " + userId + ", size: " + activities.size());
                for (Activity activity : activities) {
                    mLocalActivityDataSource.createActivity(activity);
                }
            }
        });
    }
}
