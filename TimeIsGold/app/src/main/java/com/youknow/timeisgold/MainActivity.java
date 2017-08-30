package com.youknow.timeisgold;

import com.youknow.timeisgold.view.start.StartActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getSharedPreferences(getString(R.string.pref_setting), MODE_PRIVATE);
        boolean isFirstTime = pref.getBoolean(getString(R.string.pref_first_time), true);
        if (isFirstTime) {
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }
    }
}
