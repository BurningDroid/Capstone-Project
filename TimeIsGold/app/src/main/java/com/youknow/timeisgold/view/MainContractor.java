package com.youknow.timeisgold.view;

import com.youknow.timeisgold.data.Activity;

/**
 * Created by Aaron on 04/09/2017.
 */

public interface MainContractor {
    interface View {
        void loadCategoryDetails(Activity activity);
    }
    interface Presenter {
        void getRunningActivity();

        void signOut();
    }
}
