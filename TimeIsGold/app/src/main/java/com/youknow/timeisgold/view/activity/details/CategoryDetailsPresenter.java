package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;

import android.content.Context;

/**
 * Created by Aaron on 04/09/2017.
 */

public class CategoryDetailsPresenter implements CategoryDetailsContract.Presenter {

    private static CategoryDetailsPresenter INSTANCE = null;

    private CategoryDetailsContract.View mView;
    private Context mContext;
    private ActivityDataSource mActivityDataSource;
    private CategoryDataSource mCategoryDataSource;

    private CategoryDetailsPresenter(Context context) {
        mContext = context;
        mActivityDataSource = Injection.provideActivityDataSource(context);
        mCategoryDataSource = Injection.provideCategoryDataSource(context);
    }

    public static CategoryDetailsPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryDetailsPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void setView(CategoryDetailsContract.View view) {
        mView = view;
    }

    @Override
    public void startActivity(Category category) {
        Activity activity = new Activity();
        activity.setCategoryId(category.getId());
        activity.setDesc("");
        activity.setRunning(true);
        activity.setStartTime(System.currentTimeMillis());
        mActivityDataSource.createActivity(activity);

        mView.showRunningState(category, activity.getStartTime());
    }

    @Override
    public void stopActivity(Activity activity) {
        activity.setEndTime(System.currentTimeMillis());
        activity.setSpendTime(activity.getEndTime() - activity.getStartTime());
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
