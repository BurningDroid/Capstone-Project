package com.youknow.timeisgold.view.history;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 09/09/2017.
 */

public class HistoryPresenter implements HistoryContract.Presenter {

    private static HistoryPresenter INSTANCE = null;

    private HistoryContract.View mView;
    private Context mContext;
    private CategoryDataSource mCategoryDataSource;
    private ActivityDataSource mActivityDataSource;

    private HistoryPresenter(Context context) {
        mContext = context;
    }

    public static HistoryPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new HistoryPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void setView(HistoryContract.View view) {
        mView = view;
        mCategoryDataSource = Injection.provideCategoryDataSource(mContext);
        mActivityDataSource = Injection.provideActivityDataSource(mContext);
    }

    @Override
    public List<History> getAllHistory() {
        List<Activity> activityList = mActivityDataSource.getAllActivity();
        List<History> histories = new ArrayList<>();

        for (Activity activity : activityList) {
            Category category = mCategoryDataSource.getCategory(activity.getCategoryId());
            History history = new History(activity);
            history.setCategory(category);
            histories.add(history);
        }

        return histories;
    }
}
