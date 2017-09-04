package com.youknow.timeisgold.view;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.source.ActivityDataSource;

import android.content.Context;

/**
 * Created by Aaron on 04/09/2017.
 */

public class MainPresenter implements MainContractor.Presenter {

    private static MainPresenter INSTANCE;

    private MainContractor.View mView;
    private ActivityDataSource mActivityDataSource;

    private MainPresenter(Context context) {
        mView = (MainContractor.View) context;
        mActivityDataSource = Injection.provideActivityDataSource(context);
    }

    public static MainPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MainPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public Activity getRunningActivity() {
        return mActivityDataSource.getRunningActivity();
    }
}
