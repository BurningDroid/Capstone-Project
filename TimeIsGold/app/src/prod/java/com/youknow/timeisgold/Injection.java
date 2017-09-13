package com.youknow.timeisgold;

import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.data.source.UserDataSource;
import com.youknow.timeisgold.data.source.firebase.FirebaseActivityDataSource;
import com.youknow.timeisgold.data.source.firebase.FirebaseCategoryDataSource;
import com.youknow.timeisgold.data.source.firebase.FirebaseUserDataSource;
import com.youknow.timeisgold.data.source.local.LocalActivityDataSource;
import com.youknow.timeisgold.data.source.local.LocalCategoryDataSource;
import com.youknow.timeisgold.service.ActivityService;
import com.youknow.timeisgold.service.CategoryService;
import com.youknow.timeisgold.service.UserService;
import com.youknow.timeisgold.service.impl.ActivityServiceImpl;
import com.youknow.timeisgold.service.impl.CategoryServiceImpl;
import com.youknow.timeisgold.service.impl.UserServiceImpl;

import android.content.Context;

/**
 * Created by Aaron on 31/08/2017.
 */

public class Injection {
    public static UserDataSource provideUserDataSource() {
        return FirebaseUserDataSource.getInstance();
    }

    public static CategoryDataSource provideCategoryDataSource(Context context) {
        return LocalCategoryDataSource.getInstance(context);
    }

    public static FirebaseCategoryDataSource provideRemoteCategoryDataSource() {
        return FirebaseCategoryDataSource.getInstance();
    }

    public static ActivityDataSource provideLocalActivityDataSource(Context context) {
        return LocalActivityDataSource.getInstance(context);
    }

    public static FirebaseActivityDataSource provideRemoteActivityDataSource() {
        return FirebaseActivityDataSource.getInstance();
    }

    public static UserService provideUserService(Context context) {
        return UserServiceImpl.getInstance(context);
    }

    public static CategoryService provideCategoryService(Context context) {
        return CategoryServiceImpl.getInstance(context);
    }

    public static ActivityService provideActivityService(Context context) {
        return ActivityServiceImpl.getInstance(context);
    }

}
