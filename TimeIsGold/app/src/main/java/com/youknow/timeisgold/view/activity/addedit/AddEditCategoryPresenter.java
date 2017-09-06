package com.youknow.timeisgold.view.activity.addedit;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.source.CategoryDataSource;

import android.content.Context;

/**
 * Created by Aaron on 03/09/2017.
 */

public class AddEditCategoryPresenter implements AddEditCategoryContract.Presenter {

    private static AddEditCategoryPresenter INSTANCE;

    private AddEditCategoryContract.View mView;
    private CategoryDataSource mCategoryDataSource;

    private AddEditCategoryPresenter() {

    }

    public static AddEditCategoryPresenter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AddEditCategoryPresenter();
        }

        return INSTANCE;
    }

    @Override
    public void setView(AddEditCategoryContract.View view) {
        mView = view;
        mCategoryDataSource = Injection.provideCategoryDataSource((Context) mView);
    }

    @Override
    public void createCategory(Category category) {
        mCategoryDataSource.createCategory(category);
        mView.finish();
    }

    @Override
    public void updateCategory(Category category) {
        mCategoryDataSource.updateCategory(category);
        mView.finish();
    }
}
