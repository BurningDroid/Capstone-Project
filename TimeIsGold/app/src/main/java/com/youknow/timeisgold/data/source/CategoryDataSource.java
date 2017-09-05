package com.youknow.timeisgold.data.source;

import com.youknow.timeisgold.data.Category;

import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public interface CategoryDataSource {

    void createCategory(Category category);

    void updateCategory(Category category);

    Category getCategory(long id);

    List<Category> getAllCategory();

    void deleteCategory(long id);
}
