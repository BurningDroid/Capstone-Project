package com.youknow.timeisgold.data.database;

import android.net.Uri;

/**
 * Created by Aaron on 02/09/2017.
 */

public class ActivityContract {
    public static final String CONTENT_AUTHORITY = "com.youknow.timeisgold";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String TABLE_NAME = "ACTIVITY_TABLE";

    private static final String ACTIVITIES = "activities";

    private ActivityContract() {
    }

    interface Columns {
        String _ID = "_id";
        String IS_RUNNING = "is_running";
        String START_TIME = "start_time";
        String END_TIME = "end_time";
        String SPEND_TIME = "spend_time";
        String DESC = "desc";
        String CATEGORY_ID = "category_id";
    }

    public static class Activities implements Columns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.youknow.timeisgold." + ACTIVITIES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.youknow.timeisgold." + ACTIVITIES;

        public static final String DEFAULT_SORT = END_TIME + " DESC";

        public static Uri buildDirUri() {
            return BASE_URI.buildUpon().appendPath(ACTIVITIES).build();
        }

        /** Matches: /items/[_id]/ */
        public static Uri buildItemUri(long _id) {
            return BASE_URI.buildUpon().appendPath(ACTIVITIES).appendPath(Long.toString(_id)).build();
        }

        /** Read item ID item detail URI. */
        public static long getItemId(Uri activityUri) {
            return Long.parseLong(activityUri.getPathSegments().get(1));
        }
    }

}
