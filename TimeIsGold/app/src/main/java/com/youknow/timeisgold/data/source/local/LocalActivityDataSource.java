package com.youknow.timeisgold.data.source.local;

import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.database.ActivityContract;
import com.youknow.timeisgold.data.source.ActivityDataSource;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Aaron on 31/08/2017.
 */

public class LocalActivityDataSource implements ActivityDataSource {

    private static final String TAG = LocalActivityDataSource.class.getSimpleName();
    private static LocalActivityDataSource INSTANCE;

    private Context mContext;

    private LocalActivityDataSource(Context context) {
        mContext = context;
    }

    public static ActivityDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalActivityDataSource(context);
        }

        return INSTANCE;
    }

    @Override
    public void saveActivity(Activity activity) {
        ContentValues newValues = new ContentValues();
        newValues.put(ActivityContract.Activities.START_TIME, activity.getStartTime());
        if (activity.getEndTime() > 0) {
            newValues.put(ActivityContract.Activities.END_TIME, activity.getEndTime());
            newValues.put(ActivityContract.Activities.SPEND_TIME, activity.getSpendTime());
        }
        newValues.put(ActivityContract.Activities.DESC, activity.getDesc());
        newValues.put(ActivityContract.Activities.CATEGORY_ID, activity.getCategoryId());

        Uri uri = mContext.getContentResolver().insert(ActivityContract.Activities.buildDirUri(), newValues);
        Log.d(TAG, "[TIG] saveActivity - " + ContentUris.parseId(uri));
    }

    @Override
    public Activity getActivity(long id) {
        Activity activity = null;
        Cursor cursor = mContext.getContentResolver().query(ActivityContract.Activities.buildItemUri(id), null, null, null, null);
        if (cursor == null || cursor.getCount() < 1) {
            Log.e(TAG, "[TIG] getActivity - id[" + id + "] is null");
        } else {
            cursor.moveToFirst();
            activity = new Activity();
            activity.setId(id);
            activity.setStartTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.START_TIME)));
            activity.setEndTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.END_TIME)));
            activity.setSpendTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.SPEND_TIME)));
            boolean isFavorite = (cursor.getInt(cursor.getColumnIndex(ActivityContract.Activities.IS_FAVORITE)) == 0) ? false : true;
            activity.setFavorite(isFavorite);
            activity.setDesc(cursor.getString(cursor.getColumnIndex(ActivityContract.Activities.DESC)));
            activity.setCategoryId(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.CATEGORY_ID)));
        }

        return activity;
    }

    @Override
    public void deleteActivity(long id) {

    }
}
