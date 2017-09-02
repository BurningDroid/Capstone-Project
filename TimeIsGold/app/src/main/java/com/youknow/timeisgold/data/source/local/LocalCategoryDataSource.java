package com.youknow.timeisgold.data.source.local;

import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.Type;
import com.youknow.timeisgold.data.database.CategoryContract;
import com.youknow.timeisgold.data.source.CategoryDataSource;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public class LocalCategoryDataSource implements CategoryDataSource {

    private static final String TAG = LocalCategoryDataSource.class.getSimpleName();
    private static LocalCategoryDataSource INSTANCE;

    private Context mContext;

    private LocalCategoryDataSource(Context context) {
        mContext = context;
    }

    public static CategoryDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalCategoryDataSource(context);
        }

        return INSTANCE;
    }

    @Override
    public void saveCategory(Category category) {
        ContentValues newValues = new ContentValues();
        newValues.put(CategoryContract.Categories.NAME, category.getName());
        newValues.put(CategoryContract.Categories.COLOR, category.getColor());
        newValues.put(CategoryContract.Categories.ICON, category.getIcon());
        newValues.put(CategoryContract.Categories.TYPE, category.getType().name());

        Uri uri = mContext.getContentResolver().insert(CategoryContract.Categories.buildDirUri(), newValues);
        Log.d(TAG, "[TIG] saveCategory - " + ContentUris.parseId(uri));
    }

    @Override
    public Category getCategory(long id) {
        Category category = null;
        Cursor cursor = mContext.getContentResolver().query(CategoryContract.Categories.buildItemUri(id), null, null, null, null);
        if (cursor == null || cursor.getCount() < 1) {
            Log.e(TAG, "[TIG] getCategory - id[" + id + "] is null");
        } else {
            cursor.moveToFirst();
            category = new Category();
            category.setId(id);
            category.setName(cursor.getString(cursor.getColumnIndex(CategoryContract.Categories.NAME)));
            category.setColor(cursor.getInt(cursor.getColumnIndex(CategoryContract.Categories.COLOR)));
            category.setIcon(cursor.getInt(cursor.getColumnIndex(CategoryContract.Categories.ICON)));
            category.setType(Type.valueOf(cursor.getString(cursor.getColumnIndex(CategoryContract.Categories.TYPE))));
        }

        return category;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(CategoryContract.Categories.buildDirUri(), null, null, null, null);
        if (cursor == null || cursor.getCount() < 1) {
            Log.e(TAG, "[TIG] getAllCategory - size is zero");
        } else {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Category category = new Category();
                category.setId(cursor.getLong(cursor.getColumnIndex(CategoryContract.Categories._ID)));
                category.setName(cursor.getString(cursor.getColumnIndex(CategoryContract.Categories.NAME)));
                category.setColor(cursor.getInt(cursor.getColumnIndex(CategoryContract.Categories.COLOR)));
                category.setIcon(cursor.getInt(cursor.getColumnIndex(CategoryContract.Categories.ICON)));
                category.setType(Type.valueOf(cursor.getString(cursor.getColumnIndex(CategoryContract.Categories.TYPE))));
                categories.add(category);
            }
        }

        return categories;
    }

    @Override
    public void deleteCategory(long id) {

    }
}
