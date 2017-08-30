package com.youknow.timeisgold;

import com.google.firebase.database.FirebaseDatabase;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aaron on 30/08/2017.
 */

public class MainApplication extends Application {

    private static FirebaseDatabase database;

    public static Context getContext() {
        return getContext();
    }

    public static FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(false);
        }

        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
