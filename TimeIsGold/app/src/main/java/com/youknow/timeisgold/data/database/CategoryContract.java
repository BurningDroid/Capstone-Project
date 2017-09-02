package com.youknow.timeisgold.data.database;

import android.net.Uri;

/**
 * Created by Aaron on 02/09/2017.
 */

public class CategoryContract {
    public static final String CONTENT_AUTHORITY = "com.youknow.timeisgold";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String TABLE_NAME = "CATEGORY_TABLE";

    private static final String CATEGORIES = "categories";

    private CategoryContract() {
    }

    public interface Columns {
        String _ID = "_id";
        String NAME = "name";
        String COLOR = "color";
        String ICON = "icon";
        String TYPE = "type";
    }

    public static class Categories implements Columns {
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.youknow.timeisgold." + CATEGORIES;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.youknow.timeisgold." + CATEGORIES;

        public static Uri buildDirUri() {
            return BASE_URI.buildUpon().appendPath(CATEGORIES).build();
        }

        /** Matches: /items/[_id]/ */
        public static Uri buildItemUri(long _id) {
            return BASE_URI.buildUpon().appendPath(CATEGORIES).appendPath(Long.toString(_id)).build();
        }

        /** Read item ID item detail URI. */
        public static long getItemId(Uri activityUri) {
            return Long.parseLong(activityUri.getPathSegments().get(1));
        }
    }

}
