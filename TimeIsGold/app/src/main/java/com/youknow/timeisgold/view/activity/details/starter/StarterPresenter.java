package com.youknow.timeisgold.view.activity.details.starter;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.utils.DateTimeUtil;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 10/09/2017.
 */

public class StarterPresenter implements StarterContract.Presenter {
    private static final String TAG = StarterPresenter.class.getSimpleName();

    private static StarterContract.Presenter INSTANCE;

    private StarterContract.View mView;
    private Context mContext;
    private ActivityDataSource mActivityDataSource;
    private CategoryDataSource mCategoryDataSource;

    private StarterPresenter(Context context) {
        mContext = context;
        mActivityDataSource = Injection.provideActivityDataSource(context);
        mCategoryDataSource = Injection.provideCategoryDataSource(context);
    }

    public static StarterContract.Presenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StarterPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void setView(StarterContract.View view) {
        mView = view;
    }

    @Override
    public void get7dayData(Category category) {
        long startDate = DateTimeUtil.getBeforeDateTime(7);
        List<Activity> activityList = mActivityDataSource.getActivities(category.getId(), startDate);
        if (activityList.isEmpty()) {
            mView.onLoadedChartData(null, null);
            Log.d(TAG, "[TIG] get7dayData is empty");
            return;
        }

        Map<String, Float> dataMap = DateTimeUtil.getDateMap(7);
        for (Activity activity : activityList) {
            String date = DateTimeUtil.DATE_FORMAT_SHORT.format(new Date(activity.getStartTime()));
            if (dataMap.containsKey(date)) {
                Log.d(TAG, "[TIG] getElapsedTime: " + DateTimeUtil.getElapsedTime(activity.getRelElapsedTime()) + ", " + DateTimeUtil.getElapsedHour(activity.getRelElapsedTime()));
                float hour = DateTimeUtil.getElapsedHour(activity.getRelElapsedTime()) + dataMap.get(date);
                dataMap.put(date, hour);
            }
        }

        List<BarEntry> entries = new ArrayList<>();
        float idx = 0f;
        for (String date : dataMap.keySet()) {
            entries.add(new BarEntry(idx++, dataMap.get(date)));
        }

        BarDataSet set = new BarDataSet(entries, mContext.getString(R.string.chart_unit_hour));
        BarData data = new BarData(set);
        data.setBarWidth(0.9f);
        mView.onLoadedChartData(dataMap.keySet().toArray(new String[dataMap.keySet().size()]), data);
    }

    @Override
    public void startActivity(Category category) {
        Activity activity = new Activity();
        activity.setCategoryId(category.getId());
        activity.setDesc("");
        activity.setRunning(true);
        activity.setStartTime(System.currentTimeMillis());
        activity.setRelStartTime(SystemClock.elapsedRealtime());

        long activityId = mActivityDataSource.createActivity(activity);
        activity.setId(activityId);
        mView.showRunningState(activity);
    }

    @Override
    public void stopActivity(Activity activity) {
        activity.setEndTime(System.currentTimeMillis());
        activity.setRelEndTime(SystemClock.elapsedRealtime());
        activity.setRelElapsedTime(activity.getRelEndTime() - activity.getRelStartTime());
        activity.setRunning(false);
        mActivityDataSource.updateActivity(activity);
        mView.finish();
    }

    @Override
    public void getCategory(long categoryId) {
        Category category = mCategoryDataSource.getCategory(categoryId);
        mView.onLoadedCategory(category);
    }

}
