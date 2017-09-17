package com.youknow.timeisgold.view.widget;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.service.CategoryService;

import android.content.Context;

import java.util.List;

/**
 * Created by Aaron on 17/09/2017.
 */

public class CategorySelectorPresenter implements CategorySelectorContract.Presenter {

    private static CategorySelectorPresenter INSTANCE;

    CategorySelectorContract.View mView;
    private CategoryService mCategoryService;

    private CategorySelectorPresenter(Context context) {
        mCategoryService = Injection.provideCategoryService(context);
    }

    public static CategorySelectorPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategorySelectorPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void setView(CategorySelectorContract.View view) {
        mView = view;
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
}
