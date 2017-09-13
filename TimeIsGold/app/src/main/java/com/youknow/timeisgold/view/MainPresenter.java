package com.youknow.timeisgold.view;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.service.ActivityService;
import com.youknow.timeisgold.service.UserService;

import android.content.Context;

/**
 * Created by Aaron on 04/09/2017.
 */

public class MainPresenter implements MainContractor.Presenter {

    private static MainPresenter INSTANCE;

    private MainContractor.View mView;
    private UserService mUserService;
    private ActivityService mActivityService;

    private MainPresenter(Context context) {
        mView = (MainContractor.View) context;
        mUserService = Injection.provideUserService(context);
        mActivityService = Injection.provideActivityService(context);
    }

    public static MainPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MainPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void getRunningActivity() {
        mActivityService.getRunningActivity(new ActivityService.OnLoadedActivityListener() {
            @Override
            public void onLoadedActivity(Activity activity) {
                if (activity != null) {
                    mView.loadCategoryDetails(activity);
                }
            }
        });
    }

    @Override
    public void signOut() {
        mUserService.signOut();
    }
}
