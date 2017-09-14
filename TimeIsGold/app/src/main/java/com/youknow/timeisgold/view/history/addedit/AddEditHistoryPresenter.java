package com.youknow.timeisgold.view.history.addedit;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.service.ActivityService;

import android.content.Context;
import android.util.Log;

/**
 * Created by Aaron on 14/09/2017.
 */

public class AddEditHistoryPresenter implements AddEditHistoryContract.Presenter {

    private static final String TAG = AddEditHistoryActivity.class.getSimpleName();

    private static AddEditHistoryPresenter INSTANCE = null;

    private AddEditHistoryContract.View mView;
    private Context mContext;
    private ActivityService mActivityService;

    private AddEditHistoryPresenter(Context context) {
        mContext = context;
        mActivityService = Injection.provideActivityService(mContext);
    }

    public static AddEditHistoryPresenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AddEditHistoryPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void setView(AddEditHistoryContract.View view) {
        mView = view;
    }

    @Override
    public void saveActivity(Activity activity) {
        mActivityService.updateActivity(activity);
        mView.finish(mContext.getString(R.string.update_complete_message));
    }

    @Override
    public void getActivity(long activityId) {
        mActivityService.getActivity(activityId, new ActivityService.OnLoadedActivityListener() {
            @Override
            public void onLoadedActivity(Activity activity) {
                mView.onLoadedActivity(activity);
            }
        });
    }

    @Override
    public void deleteHistory(History history) {
        Log.d(TAG, "[TIG] deleteHistory: " + history);
        mActivityService.deleteActivity(history.getActivityId());
        mView.finish(mContext.getString(R.string.delete_complete_message));
    }
}
