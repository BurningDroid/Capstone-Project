package com.youknow.timeisgold.utils;

import com.youknow.timeisgold.R;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aaron on 31/08/2017.
 */

public class SharedPrefUtil {

    private static SharedPrefUtil INSTANCE;
    private Context mContext;

    private SharedPrefUtil(Context context) {
        mContext = context;
    }

    public static SharedPrefUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPrefUtil(context);
        }

        return INSTANCE;
    }

    public void initialize() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.pref_setting), MODE_PRIVATE).edit();
        editor.putBoolean(mContext.getString(R.string.pref_initialize), true);
        editor.apply();
    }

    public boolean isInitialized() {
        SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.pref_setting), MODE_PRIVATE);
        return pref.getBoolean(mContext.getString(R.string.pref_initialize), false);
    }
}
