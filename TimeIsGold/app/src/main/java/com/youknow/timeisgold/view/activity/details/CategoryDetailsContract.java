package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;

/**
 * Created by Aaron on 04/09/2017.
 */

public interface CategoryDetailsContract {
    interface View {

        void showRunningState(Category category, long startTime);

        void finish();

        void onLoadedCategory(Category category);
    }

    interface Presenter {

        void setView(View view);

        void getCategory(long categoryId);

        void startActivity(Category category);

        void stopActivity(Activity activity);
    }
}
