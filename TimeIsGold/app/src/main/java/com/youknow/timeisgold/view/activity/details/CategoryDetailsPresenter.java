package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.service.CategoryService;

import android.content.Context;

/**
 * Created by Aaron on 04/09/2017.
 */

public class CategoryDetailsPresenter implements CategoryDetailsContract.Presenter {

    private static final String TAG = "CategoryDetailsPresente";
    private static CategoryDetailsPresenter INSTANCE = null;

    private CategoryDetailsContract.View mView;
    private Context mContext;
    private CategoryService mCategoryService;

    private CategoryDetailsPresenter(Context context) {
        mContext = context;
        mCategoryService = Injection.provideCategoryService(context);
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
        mCategoryService.updateCategory(category);
    }

    @Override
    public void deleteCategory(Category category) {
        mCategoryService.deleteCategory(category);
        mView.deleteDone();
    }

    @Override
    public void getCategory(long categoryId) {
        mCategoryService.getCategory(categoryId, new CategoryService.OnLoadedCategoryListener() {
            @Override
            public void onLoadedCategory(Category category) {
                mView.onLoadedCategory(category);
            }
        });
    }

}
