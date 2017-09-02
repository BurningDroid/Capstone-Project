package com.youknow.timeisgold.data.provider;

import com.youknow.timeisgold.data.database.ActivityContract;
import com.youknow.timeisgold.data.database.CommonDatabase;
import com.youknow.timeisgold.data.database.CategoryContract;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public class ActivityProvider extends ContentProvider {

    private static final int ACTIVITIES = 0;
    private static final int ACTIVITIES__ID = 1;
    private static final int CATEGORIES = 2;
    private static final int CATEGORIES__ID = 3;
    private static UriMatcher sUriMatcher = buildUriMatcher();
    private SQLiteOpenHelper mOpenHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ActivityContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, "activities", ACTIVITIES);
        matcher.addURI(authority, "activities/#", ACTIVITIES__ID);
        matcher.addURI(authority, "categories", CATEGORIES);
        matcher.addURI(authority, "categories/#", CATEGORIES__ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CommonDatabase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        Cursor cursor = builder.where(selection, selectionArgs).query(db, projection, sortOrder);
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ACTIVITIES:
                return ActivityContract.Activities.CONTENT_TYPE;
            case ACTIVITIES__ID:
                return ActivityContract.Activities.CONTENT_ITEM_TYPE;
            case CATEGORIES:
                return CategoryContract.Categories.CONTENT_TYPE;
            case CATEGORIES__ID:
                return CategoryContract.Categories.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ACTIVITIES: {
                final long _id = db.insertOrThrow(ActivityContract.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return ActivityContract.Activities.buildItemUri(_id);
            }
            case CATEGORIES: {
                final long _id = db.insertOrThrow(CategoryContract.TABLE_NAME, null, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return CategoryContract.Categories.buildItemUri(_id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).delete(db);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final SelectionBuilder builder = buildSelection(uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return builder.where(selection, selectionArgs).update(db, values);

    }

    private SelectionBuilder buildSelection(Uri uri) {
        final SelectionBuilder builder = new SelectionBuilder();
        final int match = sUriMatcher.match(uri);
        return buildSelection(uri, match, builder);
    }

    private SelectionBuilder buildSelection(Uri uri, int match, SelectionBuilder builder) {
        final List<String> paths = uri.getPathSegments();
        switch (match) {
            case ACTIVITIES: {
                return builder.table(ActivityContract.TABLE_NAME);
            }
            case ACTIVITIES__ID: {
                final String _id = paths.get(1);
                return builder.table(ActivityContract.TABLE_NAME).where(ActivityContract.Activities._ID + "=?", _id);
            }
            case CATEGORIES: {
                return builder.table(CategoryContract.TABLE_NAME);
            }
            case CATEGORIES__ID: {
                final String _id = paths.get(1);
                return builder.table(CategoryContract.TABLE_NAME).where(CategoryContract.Categories._ID + "=?", _id);
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }
}
