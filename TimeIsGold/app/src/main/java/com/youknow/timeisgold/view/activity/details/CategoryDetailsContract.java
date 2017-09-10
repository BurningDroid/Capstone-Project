package com.youknow.timeisgold.view.activity.details;

import com.github.mikephil.charting.data.BarData;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;

import java.util.Set;

/**
 * Created by Aaron on 04/09/2017.
 */

public interface CategoryDetailsContract {
    interface View {

        void finish();

        void onLoadedCategory(Category category);

        void deleteDone();

    }

    interface Presenter {

        void setView(View view);

        void getCategory(long categoryId);

        void saveCategory(Category category);

        void deleteCategory(Category category);

    }
}
