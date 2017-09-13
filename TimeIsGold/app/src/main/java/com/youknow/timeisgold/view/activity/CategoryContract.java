package com.youknow.timeisgold.view.activity;

import com.youknow.timeisgold.data.Category;

import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public interface CategoryContract {
    interface View {

        void loadCategories(List<Category> categories);

        void loadCategoryDetails(Category category);

        void showMessage(String message);
    }

    interface Presenter {

        void setView(CategoryContract.View view);

        void getAllCategory();

        void hasRunningActivity(Category category);
    }
}
