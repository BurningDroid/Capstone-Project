package com.youknow.timeisgold.view.widget;

import com.youknow.timeisgold.data.Category;

import java.util.List;

/**
 * Created by Aaron on 17/09/2017.
 */

public interface CategorySelectorContract {
    interface View {

        void loadCategories(List<Category> categories);
    }

    interface Presenter {

        void setView(CategorySelectorContract.View view);

        void getAllCategory();
    }
}
