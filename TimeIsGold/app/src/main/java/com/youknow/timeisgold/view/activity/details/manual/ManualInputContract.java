package com.youknow.timeisgold.view.activity.details.manual;

import com.youknow.timeisgold.data.Activity;

/**
 * Created by Aaron on 10/09/2017.
 */

public interface ManualInputContract {
    interface View {

        void finish();
    }

    interface Presenter {

        void createNewActivity(Activity activity);

        void setView(ManualInputContract.View view);
    }
}
