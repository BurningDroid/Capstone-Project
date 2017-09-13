package com.youknow.timeisgold.service;

import com.youknow.timeisgold.data.Category;

import java.util.List;

/**
 * Created by Aaron on 14/09/2017.
 */

public interface CategoryService {
    void createCategory(Category category);

    void updateCategory(Category category);

    void getCategory(long categoryId, OnLoadedCategoryListener callback);

    void getAllCategory(OnLoadedCategoriesListener callback);

    void deleteCategory(Category category);

    interface OnLoadedCategoryListener {
        void onLoadedCategory(Category category);
    }

    interface OnLoadedCategoriesListener {
        void onLoadedCategories(List<Category> categories);
    }
}
