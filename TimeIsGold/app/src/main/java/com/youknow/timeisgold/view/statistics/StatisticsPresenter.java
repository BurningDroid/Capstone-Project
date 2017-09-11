package com.youknow.timeisgold.view.statistics;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.source.ActivityDataSource;

import android.content.Context;

/**
 * Created by Aaron on 11/09/2017.
 */

public class StatisticsPresenter implements StatisticsContract.Presenter {

    private static StatisticsPresenter INSTANCE;
    ActivityDataSource mActivityDataSource;

    private Context mContext;

    private StatisticsPresenter(Context context) {
        mContext = context;
        mActivityDataSource = Injection.provideActivityDataSource(context);
    }

    public static StatisticsContract.Presenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StatisticsPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void getActivities(int day) {

    }
}
