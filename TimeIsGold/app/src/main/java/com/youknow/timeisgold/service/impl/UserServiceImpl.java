package com.youknow.timeisgold.service.impl;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.User;
import com.youknow.timeisgold.data.source.UserDataSource;
import com.youknow.timeisgold.service.UserService;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Aaron on 31/08/2017.
 */

public class UserServiceImpl implements UserService {

    private static final String TAG = UserServiceImpl.class.getSimpleName();

    private static UserServiceImpl INSTANCE;

    private Context mContext;
    private UserDataSource mUserDataSource;

    private UserServiceImpl(Context context) {
        mContext = context;
        mUserDataSource = Injection.provideUserDataSource();
    }

    public static UserService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserServiceImpl(context);
        }

        return INSTANCE;
    }

    @Override
    public void createUser(User user) {
        mUserDataSource.saveUser(user);


    }

    @Override
    public void getUser(String id, UserDataSource.OnLoadedUserListener callback) {
        mUserDataSource.getUser(id, callback);
    }
}
