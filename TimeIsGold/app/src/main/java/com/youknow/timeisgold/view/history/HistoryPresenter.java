package com.youknow.timeisgold.view.history;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.data.database.ActivityContract;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.service.ActivityService;

import android.content.Context;
import android.database.Cursor;

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
    public void clearHistory() {
        mActivityService.deleteActivities();
        mView.showEmptyHistory();
    }

    @Override
    public List<History> convertToHistory(Cursor data) {
        List<History> histories = new ArrayList<>();

        for (data.moveToFirst(); !data.isAfterLast(); data.moveToNext()) {
            Activity activity = new Activity();
            activity.setId(data.getLong(data.getColumnIndex(ActivityContract.Activities._ID)));
            boolean isRunning = data.getInt(data.getColumnIndex(ActivityContract.Activities.IS_RUNNING)) == 0 ? false : true;
            activity.setRunning(isRunning);
            activity.setStartTime(data.getLong(data.getColumnIndex(ActivityContract.Activities.START_TIME)));
            activity.setEndTime(data.getLong(data.getColumnIndex(ActivityContract.Activities.END_TIME)));
            activity.setRelStartTime(data.getLong(data.getColumnIndex(ActivityContract.Activities.REL_START_TIME)));
            activity.setRelEndTime(data.getLong(data.getColumnIndex(ActivityContract.Activities.REL_END_TIME)));
            activity.setRelElapsedTime(data.getLong(data.getColumnIndex(ActivityContract.Activities.REL_ELAPSED_TIME)));
            activity.setDesc(data.getString(data.getColumnIndex(ActivityContract.Activities.DESC)));
            activity.setCategoryId(data.getLong(data.getColumnIndex(ActivityContract.Activities.CATEGORY_ID)));

            Category category = mCategoryDataSource.getCategory(activity.getCategoryId());
            History history = new History(activity);
            history.setCategory(category);
            histories.add(history);
        }

        return histories;
    }
}
