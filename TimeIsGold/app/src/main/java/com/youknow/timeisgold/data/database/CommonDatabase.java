package com.youknow.timeisgold.data.database;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public class CommonDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "timeisgold.db";
    private static final int DATABASE_VERSION = 1;

    public CommonDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ActivityContract.TABLE_NAME + " ("
                + ActivityContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ActivityContract.Columns.IS_RUNNING + " INTEGER NOT NULL,"
                + ActivityContract.Columns.START_TIME + " INTEGER NOT NULL,"
                + ActivityContract.Columns.END_TIME + " INTEGER,"
                + ActivityContract.Columns.REL_START_TIME + " INTEGER NOT NULL,"
                + ActivityContract.Columns.REL_END_TIME + " INTEGER,"
                + ActivityContract.Columns.REL_ELAPSED_TIME + " INTEGER,"
                + ActivityContract.Columns.DESC + " TEXT,"
                + ActivityContract.Columns.CATEGORY_ID + " INTEGER NOT NULL"
                + ")"
        );

        db.execSQL("CREATE TABLE " + CategoryContract.TABLE_NAME + " ("
                + CategoryContract.Columns._ID + " INTEGER PRIMARY KEY, "
                + CategoryContract.Columns.NAME + " TEXT NOT NULL,"
                + CategoryContract.Columns.COLOR + " INTEGER NOT NULL,"
                + CategoryContract.Columns.ICON + " INTEGER NOT NULL,"
                + CategoryContract.Columns.TYPE + " TEXT NOT NULL,"
                + CategoryContract.Columns.IS_FAVORITE + " INTEGER NOT NULL DEFAULT 1,"
                + CategoryContract.Columns.IS_DELETED + " INTEGER NOT NULL DEFAULT 0"
                + ")"
        );

        insertDefaultCategory(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ActivityContract.TABLE_NAME);
        onCreate(db);
    }

    private void insertDefaultCategory(SQLiteDatabase db) {
        List<Category> defaultCategories = new ArrayList<>();
        defaultCategories.add(new Category(1, "Work", 0xff33b5e5, R.drawable.ic_category_work, Type.WORK));
        defaultCategories.add(new Category(2, "Study", 0xffaa66cc, R.drawable.ic_category_study, Type.SELF_IMPROVEMENT));
        defaultCategories.add(new Category(3, "Moving", 0xff99cc00, R.drawable.ic_category_move, Type.ETC));
        defaultCategories.add(new Category(4, "Tea time", 0xffffbb33, R.drawable.ic_category_tea_time, Type.REFRESH));
        defaultCategories.add(new Category(5, "SNS", 0xffff4444, R.drawable.ic_category_sns, Type.WASTE));
        defaultCategories.add(new Category(6, "Game", 0xff0099cc, R.drawable.ic_category_game, Type.WASTE));
        defaultCategories.add(new Category(7, "Sleep", 0xff9933cc, R.drawable.ic_category_sleep, Type.LIFE));
        defaultCategories.add(new Category(8, "Eating", 0xff669900, R.drawable.ic_category_eating, Type.LIFE));
        defaultCategories.add(new Category(9, "Exercise", 0xffff8800, R.drawable.ic_category_exercise, Type.LIFE));
        defaultCategories.add(new Category(10, "Shopping", 0xffcc0000, R.drawable.ic_category_shopping, Type.LIFE));
        defaultCategories.add(new Category(11, "Cleaning", 0xff33b5e5, R.drawable.ic_category_cleaning, Type.LIFE));
        defaultCategories.add(new Category(12, "Shower", 0xffaa66cc, R.drawable.ic_category_shower, Type.LIFE));

        for (Category category : defaultCategories) {
            ContentValues values = new ContentValues();
            values.put(CategoryContract.Columns.NAME, category.getName());
            values.put(CategoryContract.Columns.COLOR, category.getColor());
            values.put(CategoryContract.Columns.ICON, category.getIcon());
            values.put(CategoryContract.Columns.TYPE, category.getType().name());
            db.insert(CategoryContract.TABLE_NAME, null, values);
        }
    }

}
