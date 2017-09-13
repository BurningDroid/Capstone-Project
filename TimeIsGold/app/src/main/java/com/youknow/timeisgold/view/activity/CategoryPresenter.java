package com.youknow.timeisgold.view.activity;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.service.ActivityService;
import com.youknow.timeisgold.service.CategoryService;

import android.content.Context;

import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public class CategoryPresenter implements CategoryContract.Presenter {

    private static CategoryPresenter INSTANCE = null;

    private CategoryContract.View mView;
    private Context mContext;
    private CategoryService mCategoryService;
    private ActivityService mActivityService;

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
        mCategoryService = Injection.provideCategoryService(mContext);
        mActivityService = Injection.provideActivityService(mContext);
    }

    @Override
    public void getAllCategory() {
        mCategoryService.getAllCategory(new CategoryService.OnLoadedCategoriesListener() {
            @Override
            public void onLoadedCategories(List<Category> categories) {
                mView.loadCategories(categories);
            }
        });
    }

    @Override
    public void hasRunningActivity(final Category category) {
        mActivityService.getRunningActivity(new ActivityService.OnLoadedActivityListener() {
            @Override
            public void onLoadedActivity(Activity activity) {
                if (activity == null) {
                    mView.loadCategoryDetails(category);
                } else {
                    mView.showMessage(mContext.getString(R.string.there_is_an_already_running_activity));
                }
            }
        });
    }

}
