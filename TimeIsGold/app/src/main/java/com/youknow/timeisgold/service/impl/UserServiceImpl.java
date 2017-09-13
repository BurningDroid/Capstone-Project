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
import com.youknow.timeisgold.utils.SharedPrefUtil;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Aaron on 31/08/2017.
 */

public class UserServiceImpl implements UserService {

    private static final String TAG = UserServiceImpl.class.getSimpleName();

    private static UserServiceImpl INSTANCE;

    private Context mContext;
    private UserDataSource mUserDataSource;
    private CategoryDataSource mLocalCategoryDataSource;
    private FirebaseCategoryDataSource mRemoteCategoryDataSource;
    private ActivityDataSource mLocalActivityDataSource;
    private FirebaseActivityDataSource mRemoteActivityDataSource;

    private UserServiceImpl(Context context) {
        mContext = context;
        mUserDataSource = Injection.provideUserDataSource();
        mLocalCategoryDataSource = Injection.provideCategoryDataSource(mContext);
        mRemoteCategoryDataSource = Injection.provideRemoteCategoryDataSource();
        mLocalActivityDataSource = Injection.provideLocalActivityDataSource(mContext);
        mRemoteActivityDataSource = Injection.provideRemoteActivityDataSource();
    }

    public static UserService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserServiceImpl(context);
        }

        return INSTANCE;
    }

    @Override
    public boolean isAuthenticated() {
        return SharedPrefUtil.getInstance(mContext).isAuthenticated();
    }

    @Override
    public void authenticate(final User user, AuthListner callback) {
        SharedPrefUtil.getInstance(mContext).authenticate(user.getId());

        mUserDataSource.getUser(user.getId(), new UserDataSource.OnLoadedUserListener() {
            @Override
            public void onLoadedUser(User u) {
                syncCategoryFromLocalToRemote(user.getId());
                syncActivityFromLocalToRemote(user.getId());

                if (u == null) {
                    Log.d(TAG, "[TIG] authenticate - Create a New User: " + user);
                    mUserDataSource.saveUser(user);
                } else {
                    Log.d(TAG, "[TIG] authenticate - This user already exist: " + user);
                    syncCategoryFromRemoteToLocal(user.getId());
                    syncActivityFromRemoteToLocal(user.getId());
                }
            }
        });
    }

    @Override
    public void signOut() {
        SharedPrefUtil.getInstance(mContext).signOut();
    }

    @Override
    public void getUser(String id, UserDataSource.OnLoadedUserListener callback) {
        mUserDataSource.getUser(id, callback);
    }

    @Override
    public String getUserId() {
        return SharedPrefUtil.getInstance(mContext).getUserId();
    }

    private void syncCategoryFromLocalToRemote(String userId) {
        List<Category> categories = mLocalCategoryDataSource.getAllCategory();
        Log.d(TAG, "[TIG] syncCategoryFromLocalToRemote - " + userId + ", size: " + categories.size());
        for(Category category : categories) {
            mRemoteCategoryDataSource.saveCategory(userId, category);
        }
    }

    private void syncActivityFromLocalToRemote(final String userId) {
        List<Activity> activities = mLocalActivityDataSource.getAllActivity();
        Log.d(TAG, "[TIG] syncActivityFromLocalToRemote - " + userId + ", size: " + activities.size());
        for(Activity activity : activities) {
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
