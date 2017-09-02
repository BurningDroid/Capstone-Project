package com.youknow.timeisgold.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.youknow.timeisgold.data.database.ActivityContract.TABLE_NAME;

/**
 * Created by Aaron on 02/09/2017.
 */

public class ActivityDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "timeisgold.db";
    private static final int DATABASE_VERSION = 1;

    public ActivityDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
            + ActivityContract.ActivityColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ActivityContract.ActivityColumns.IS_FAVORITE + " INTEGER NOT NULL DEFAULT 0,"
                + ActivityContract.ActivityColumns.START_TIME + " INTEGER NOT NULL,"
                + ActivityContract.ActivityColumns.END_TIME + " INTEGER,"
                + ActivityContract.ActivityColumns.SPEND_TIME + " INTEGER,"
                + ActivityContract.ActivityColumns.DESC + " TEXT,"
                + ActivityContract.ActivityColumns.CATEGORY_NAME + " TEXT NOT NULL,"
                + ActivityContract.ActivityColumns.CATEGORY_COLOR + " TEXT NOT NULL,"
                + ActivityContract.ActivityColumns.CATEGORY_ICON + " TEXT NOT NULL,"
                + ActivityContract.ActivityColumns.CATEGORY_TYPE + " TEXT NOT NULL"
                + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
