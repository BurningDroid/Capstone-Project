package com.youknow.timeisgold.service.impl;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.User;
import com.youknow.timeisgold.data.source.UserDataSource;
import com.youknow.timeisgold.service.UserService;
import com.youknow.timeisgold.utils.SharedPrefUtil;

import android.content.Context;

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
    public boolean isAuthenticated() {
        return SharedPrefUtil.getInstance(mContext).isAuthenticated();
    }

    @Override
    public void authenticate(final User user, final AuthListner callback) {
        SharedPrefUtil.getInstance(mContext).authenticate(user.getId());

        mUserDataSource.getUser(user.getId(), new UserDataSource.OnLoadedUserListener() {
            @Override
            public void onLoadedUser(User savedUser) {
                new DataSyncTask(mContext, callback).execute(user, savedUser);
            }
        });
    }

    @Override
    public void signOut() {
        SharedPrefUtil.getInstance(mContext).signOut();
    }

    @Override
    public void getUser(String id, UserDataSource.OnLoadedUserListener callback) {
        mUserDataSource.getUser(id, callback);
    }

    @Override
    public String getUserId() {
        return SharedPrefUtil.getInstance(mContext).getUserId();
    }

}
