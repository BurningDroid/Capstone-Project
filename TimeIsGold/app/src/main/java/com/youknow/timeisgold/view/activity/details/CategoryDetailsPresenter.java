package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.source.CategoryDataSource;

import android.content.Context;

/**
 * Created by Aaron on 04/09/2017.
 */

public class CategoryDetailsPresenter implements CategoryDetailsContract.Presenter {

    private static final String TAG = "CategoryDetailsPresente";
    private static CategoryDetailsPresenter INSTANCE = null;

    private CategoryDetailsContract.View mView;
    private Context mContext;
    private CategoryDataSource mCategoryDataSource;

    private CategoryDetailsPresenter(Context context) {
        mContext = context;
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
    public void saveCategory(Category category) {
        mCategoryDataSource.updateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        mCategoryDataSource.deleteCategory(category);
        mView.deleteDone();
    }

    @Override
    public void getCategory(long categoryId) {
        Category category = mCategoryDataSource.getCategory(categoryId);
        mView.onLoadedCategory(category);
    }

}
