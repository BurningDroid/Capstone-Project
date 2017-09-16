package com.youknow.timeisgold.data.source.local;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.data.database.ActivityContract;
import com.youknow.timeisgold.data.source.CategoryDataSource;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 16/09/2017.
 */

public class FetchHistoryTask extends AsyncTask<Void, Void, List<History>> {

    private static final String TAG = FetchHistoryTask.class.getSimpleName();

    private Context mContext;
    private CategoryDataSource mCategoryDataSource;

    public FetchHistoryTask(Context context) {
        mContext = context;
        mCategoryDataSource = Injection.provideCategoryDataSource(mContext);
    }

    @Override
    protected List<History> doInBackground(Void... params) {
        List<History> histories = new ArrayList<>();

        String clause = ActivityContract.Activities.IS_RUNNING + " = ?";
        String[] args = new String[]{"0"};

        Cursor cursor = mContext.getContentResolver().query(ActivityContract.Activities.buildDirUri(), null, clause, args, ActivityContract.Activities.START_TIME + " DESC");
        if (cursor != null && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Activity activity = new Activity();
                activity.setId(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities._ID)));
                boolean isRunning = cursor.getInt(cursor.getColumnIndex(ActivityContract.Activities.IS_RUNNING)) == 0 ? false : true;
                activity.setRunning(isRunning);
                activity.setStartTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.START_TIME)));
                activity.setEndTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.END_TIME)));
                activity.setRelStartTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.REL_START_TIME)));
                activity.setRelEndTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.REL_END_TIME)));
                activity.setRelElapsedTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.REL_ELAPSED_TIME)));
                activity.setDesc(cursor.getString(cursor.getColumnIndex(ActivityContract.Activities.DESC)));
                activity.setCategoryId(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.CATEGORY_ID)));

                Category category = mCategoryDataSource.getCategory(activity.getCategoryId());
                History history = new History(activity);
                history.setCategory(category);
                histories.add(history);

                Log.d(TAG, "[TIG] getHistories - " + history);
            }
        }

        return histories;
    }
}
