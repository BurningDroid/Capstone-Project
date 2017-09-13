package com.youknow.timeisgold.view.history;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.service.ActivityService;

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
    private ActivityService mActivityService;
    private CategoryDataSource mCategoryDataSource;

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
        mActivityService = Injection.provideActivityService(mContext);
    }

    @Override
    public void getAllHistory() {
        mActivityService.getAllHistory(new ActivityService.OnLoadedHistoriesListener() {
            @Override
            public void onLoadedHistories(List<History> histories) {
                mView.onLoadedHistories(histories);
            }
        });
    }

    @Override
    public void clearHistory() {
        mActivityService.deleteActivities();
        mView.showEmptyHistory();
    }
}
