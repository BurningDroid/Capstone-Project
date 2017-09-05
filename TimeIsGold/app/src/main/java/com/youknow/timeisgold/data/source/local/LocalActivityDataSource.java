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
    public void createActivity(Activity activity) {
        ContentValues contentValues = getValueActivity(activity);
        Uri uri = mContext.getContentResolver().insert(ActivityContract.Activities.buildDirUri(), contentValues);
        Log.d(TAG, "[TIG] createActivity - " + ContentUris.parseId(uri) + ", " + activity);
    }

    @Override
    public void updateActivity(Activity activity) {
        String[] args = new String[]{String.valueOf(activity.getId())};
        ContentValues contentValues = getValueActivity(activity);
        mContext.getContentResolver().update(ActivityContract.Activities.buildDirUri(), contentValues, ActivityContract.Activities._ID + " = ?", args);
        Log.d(TAG, "[TIG] updateActivity - " + activity);
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
            boolean isRunning = cursor.getInt(cursor.getColumnIndex(ActivityContract.Activities.IS_RUNNING)) == 0 ? false : true;
            activity.setRunning(isRunning);
            activity.setStartTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.START_TIME)));
            activity.setEndTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.END_TIME)));
            activity.setRelStartTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.REL_START_TIME)));
            activity.setRelEndTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.REL_END_TIME)));
            activity.setRelElapsedTime(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.REL_ELAPSED_TIME)));
            activity.setDesc(cursor.getString(cursor.getColumnIndex(ActivityContract.Activities.DESC)));
            activity.setCategoryId(cursor.getLong(cursor.getColumnIndex(ActivityContract.Activities.CATEGORY_ID)));
        }

        return activity;
    }

    @Override
    public void deleteActivity(long id) {

    }

    @Override
    public Activity getRunningActivity() {
        Activity activity = null;
        String[] args = new String[]{"1"};
        Cursor cursor = mContext.getContentResolver().query(ActivityContract.Activities.buildDirUri(), null, ActivityContract.Activities.IS_RUNNING + " = ?", args, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            activity = new Activity();
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
        }

        Log.d(TAG, "[TIG] getRunningActivity - " + activity);
        return activity;
    }

    private ContentValues getValueActivity(Activity activity) {
        ContentValues newValues = new ContentValues();
        newValues.put(ActivityContract.Activities.START_TIME, activity.getStartTime());
        newValues.put(ActivityContract.Activities.REL_START_TIME, activity.getRelStartTime());
        int isRunning = activity.isRunning() ? 1 : 0;
        newValues.put(ActivityContract.Activities.IS_RUNNING, isRunning);
        if (activity.getEndTime() > 0) {
            newValues.put(ActivityContract.Activities.END_TIME, activity.getEndTime());
            newValues.put(ActivityContract.Activities.REL_END_TIME, activity.getRelEndTime());
            newValues.put(ActivityContract.Activities.REL_ELAPSED_TIME, activity.getRelElapsedTime());
        }
        newValues.put(ActivityContract.Activities.DESC, activity.getDesc());
        newValues.put(ActivityContract.Activities.CATEGORY_ID, activity.getCategoryId());

        return newValues;
    }
}
