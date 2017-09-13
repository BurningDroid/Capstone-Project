package com.youknow.timeisgold.service.impl;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.data.source.firebase.FirebaseCategoryDataSource;
import com.youknow.timeisgold.service.CategoryService;
import com.youknow.timeisgold.service.UserService;

import android.content.Context;
import android.util.Log;

/**
 * Created by Aaron on 14/09/2017.
 */

public class CategoryServiceImpl implements CategoryService {

    private static final String TAG = CategoryServiceImpl.class.getSimpleName();

    private static CategoryServiceImpl INSTANCE;

    private UserService mUserService;
    private CategoryDataSource mLocalCategoryDataSource;
    private FirebaseCategoryDataSource mRemoteCategoryDataSource;

    private CategoryServiceImpl(Context context) {
        mUserService = Injection.provideUserService(context);
        mLocalCategoryDataSource = Injection.provideCategoryDataSource(context);
        mRemoteCategoryDataSource = Injection.provideRemoteCategoryDataSource();
    }

    public static CategoryService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CategoryServiceImpl(context);
        }

        return INSTANCE;
    }

    @Override
    public void createCategory(Category category) {
        Log.d(TAG, "[TIG][service] createCategory: " + category);
        mLocalCategoryDataSource.createCategory(category);

        if (mUserService.isAuthenticated()) {
            String userId = mUserService.getUserId();
            mRemoteCategoryDataSource.saveCategory(userId, category);
        }
    }

    @Override
    public void updateCategory(Category category) {
        Log.d(TAG, "[TIG][service] updateCategory: " + category);
        mLocalCategoryDataSource.updateCategory(category);

        if (mUserService.isAuthenticated()) {
            String userId = mUserService.getUserId();
            mRemoteCategoryDataSource.saveCategory(userId, category);
        }
    }

    @Override
    public void getCategory(long categoryId, OnLoadedCategoryListener callback) {
        Log.d(TAG, "[TIG][service] getCategory: " + categoryId);
        callback.onLoadedCategory(mLocalCategoryDataSource.getCategory(categoryId));
    }

    @Override
    public void getAllCategory(OnLoadedCategoriesListener callback) {
        Log.d(TAG, "[TIG][service] getAllCategory");
        callback.onLoadedCategories(mLocalCategoryDataSource.getAllCategory());
    }

    @Override
    public void deleteCategory(Category category) {
        Log.d(TAG, "[TIG][service] deleteCategory: " + category);
        category.setDeleted(true);
        mLocalCategoryDataSource.deleteCategory(category);

        if (mUserService.isAuthenticated()) {
            String userId = mUserService.getUserId();
            mRemoteCategoryDataSource.deleteCategory(userId, category);
        }

    }

}
