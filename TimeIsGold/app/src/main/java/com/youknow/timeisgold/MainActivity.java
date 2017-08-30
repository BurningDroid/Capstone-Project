package com.youknow.timeisgold;

import com.youknow.timeisgold.utils.SharedPrefUtil;
import com.youknow.timeisgold.view.start.StartActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final static boolean NOT_INITIALIZED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (NOT_INITIALIZED == SharedPrefUtil.getInstance(this).isInitialized()) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }
    }
}
