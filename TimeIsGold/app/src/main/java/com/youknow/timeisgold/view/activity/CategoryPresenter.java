package com.youknow.timeisgold.view.activity;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;

import android.content.Context;

import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public class CategoryPresenter implements CategoryContract.Presenter {

    private static CategoryPresenter INSTANCE = null;

    private CategoryContract.View mView;
    private Context mContext;
    private CategoryDataSource mCategoryDataSource;
    private ActivityDataSource mActivityDataSource;

    private CategoryPresenter(Context context) {
        mContext = context;
    }

    public static CategoryPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void setView(CategoryContract.View view) {
        mView = view;
        mCategoryDataSource = Injection.provideCategoryDataSource(mContext);
        mActivityDataSource = Injection.provideActivityDataSource(mContext);
    }

    @Override
    public List<Category> getAllCategory() {
        return mCategoryDataSource.getAllCategory();
    }

    @Override
    public boolean hasRunningActivity() {
        return mActivityDataSource.getRunningActivity() != null;
    }

}
