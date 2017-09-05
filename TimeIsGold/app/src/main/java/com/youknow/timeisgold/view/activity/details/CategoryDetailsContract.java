package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;

/**
 * Created by Aaron on 04/09/2017.
 */

public interface CategoryDetailsContract {
    interface View {

        void showRunningState(Activity activity);

        void finish();

        void onLoadedCategory(Category category);

        void deleteDone();
    }

    interface Presenter {

        void setView(View view);

        void getCategory(long categoryId);

        void startActivity(Category category);

        void stopActivity(Activity activity);

        void saveCategory(Category category);

        void deleteCategory(Category category);
    }
}
