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
    private String mUserId;

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

    public void authenticate(String userId) {
        mUserId = userId;
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.pref_setting), MODE_PRIVATE).edit();
        editor.putString(mContext.getString(R.string.pref_user_id), userId);
        editor.apply();
    }

    public boolean isAuthenticated() {
        if (mUserId == null) {
            SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.pref_setting), MODE_PRIVATE);
            mUserId = pref.getString(mContext.getString(R.string.pref_user_id), null);
        }

        return (mUserId != null) ? true : false;
    }

    public void signOut() {
        mUserId = null;
        mContext.getSharedPreferences(mContext.getString(R.string.pref_setting), MODE_PRIVATE).edit().clear().apply();
    }

    public String getUserId() {
        if (mUserId == null) {
            SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.pref_setting), MODE_PRIVATE);
            mUserId = pref.getString(mContext.getString(R.string.pref_user_id), null);
        }

        return mUserId;
    }
}
