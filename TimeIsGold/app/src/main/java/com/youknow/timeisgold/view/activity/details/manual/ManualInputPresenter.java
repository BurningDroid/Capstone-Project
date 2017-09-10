package com.youknow.timeisgold.view.activity.details.manual;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.source.ActivityDataSource;

import android.content.Context;

/**
 * Created by Aaron on 10/09/2017.
 */

public class ManualInputPresenter implements ManualInputContract.Presenter {

    private static ManualInputPresenter INSTANCE;

    ManualInputContract.View mView;
    private Context mContext;
    private ActivityDataSource mActivityDataSource;

    private ManualInputPresenter(Context context) {
        mContext = context;
        mActivityDataSource = Injection.provideActivityDataSource(mContext);
    }

    public static ManualInputContract.Presenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ManualInputPresenter(context);
        }
        return INSTANCE;
    }

    @Override
    public void createNewActivity(Activity activity) {
        mActivityDataSource.createActivity(activity);
        mView.finish();
    }

    @Override
    public void setView(ManualInputContract.View view) {
        mView = view;
    }
}
