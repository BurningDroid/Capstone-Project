package com.youknow.timeisgold.view.history.addedit;

import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.History;

/**
 * Created by Aaron on 14/09/2017.
 */

public interface AddEditHistoryContract {
    interface View {

        void finish(String string);

        void onLoadedActivity(Activity activity);
    }

    interface Presenter {

        void setView(AddEditHistoryContract.View view);

        void saveActivity(Activity activity);

        void getActivity(long activityId);

        void deleteHistory(History history);
    }
}
