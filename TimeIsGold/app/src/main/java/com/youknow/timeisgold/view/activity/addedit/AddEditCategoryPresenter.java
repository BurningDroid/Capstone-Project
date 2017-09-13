package com.youknow.timeisgold.view.activity.addedit;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.service.CategoryService;

import android.content.Context;

/**
 * Created by Aaron on 03/09/2017.
 */

public class AddEditCategoryPresenter implements AddEditCategoryContract.Presenter {

    private static AddEditCategoryPresenter INSTANCE;

    private Context mContext;
    private AddEditCategoryContract.View mView;
    private CategoryService mCategoryService;

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
        mContext = (Context) view;
        mView = view;
        mCategoryService = Injection.provideCategoryService(mContext);
    }

    @Override
    public void createCategory(Category category) {
        mCategoryService.createCategory(category);
        mView.finish(mContext.getString(R.string.category_is_saved));
    }

    @Override
    public void updateCategory(Category category) {
        mCategoryService.updateCategory(category);
        mView.finish(mContext.getString(R.string.category_is_saved));
    }

    @Override
    public void deleteCategory(Category category) {
        mCategoryService.deleteCategory(category);
        mView.finish(mContext.getString(R.string.delete_category_done));
    }
}
