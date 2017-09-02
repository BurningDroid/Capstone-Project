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
                + ActivityContract.Columns.IS_FAVORITE + " INTEGER NOT NULL DEFAULT 0,"
                + ActivityContract.Columns.START_TIME + " INTEGER NOT NULL,"
                + ActivityContract.Columns.END_TIME + " INTEGER,"
                + ActivityContract.Columns.SPEND_TIME + " INTEGER,"
                + ActivityContract.Columns.DESC + " TEXT,"
                + ActivityContract.Columns.CATEGORY_ID + " INTEGER NOT NULL"
                + ")"
        );

        db.execSQL("CREATE TABLE " + CategoryContract.TABLE_NAME + " ("
                + CategoryContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CategoryContract.Columns.NAME + " TEXT NOT NULL,"
                + CategoryContract.Columns.COLOR + " INTEGER NOT NULL,"
                + CategoryContract.Columns.ICON + " INTEGER NOT NULL,"
                + CategoryContract.Columns.TYPE + " TEXT NOT NULL"
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
        defaultCategories.add(new Category("Work", 0xffef5350, R.drawable.ic_category_work, Type.WORK));
        defaultCategories.add(new Category("Study", 0xffba68c8, R.drawable.ic_category_study, Type.SELF_IMPROVEMENT));
        defaultCategories.add(new Category("Moving", 0xff536dfe, R.drawable.ic_category_move, Type.ETC));
        defaultCategories.add(new Category("Tea time", 0xff90caf9, R.drawable.ic_category_tea_time, Type.REFRESH));
        defaultCategories.add(new Category("SNS", 0xff00b8d4, R.drawable.ic_category_sns, Type.WASTE));
        defaultCategories.add(new Category("Game", 0xff4db6ac, R.drawable.ic_category_game, Type.WASTE));
        defaultCategories.add(new Category("Sleep", 0xff66bb6a, R.drawable.ic_category_sleep, Type.LIFE));
        defaultCategories.add(new Category("Eating", 0xff689f38, R.drawable.ic_category_eating, Type.LIFE));
        defaultCategories.add(new Category("Exercise", 0xff9e9d24, R.drawable.ic_category_exercise, Type.LIFE));
        defaultCategories.add(new Category("Shopping", 0xffffd600, R.drawable.ic_category_shopping, Type.LIFE));
        defaultCategories.add(new Category("Cleaning", 0xffffab00, R.drawable.ic_category_cleaning, Type.LIFE));
        defaultCategories.add(new Category("Shower", 0xffbcaaa4, R.drawable.ic_category_shower, Type.LIFE));

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
