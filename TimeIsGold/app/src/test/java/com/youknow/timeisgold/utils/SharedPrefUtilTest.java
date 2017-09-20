package com.youknow.timeisgold.utils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import android.content.Context;
import android.content.SharedPreferences;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

/**
 * Created by Aaron on 20/09/2017.
 */

public class SharedPrefUtilTest {

    Context mContext;
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    SharedPrefUtil mSharedPrefUtil;

    @Before
    public void setup() {
        mContext = Mockito.mock(Context.class);
        mPref = Mockito.mock(SharedPreferences.class);
        mEditor = Mockito.mock(SharedPreferences.Editor.class);

        Mockito.when(mContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mPref);
        Mockito.when(mPref.edit()).thenReturn(mEditor);

        mSharedPrefUtil = SharedPrefUtil.getInstance(mContext);
    }

    @Test
    public void getInstanceTest() {
        assertNotNull(mSharedPrefUtil);
    }

}
