package com.youknow.timeisgold.view.start;

import com.google.firebase.auth.FirebaseUser;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.data.User;
import com.youknow.timeisgold.service.UserService;
import com.youknow.timeisgold.utils.SharedPrefUtil;

import android.content.Context;

/**
 * Created by Aaron on 31/08/2017.
 */

public class StartPresenter implements StartContract.Presenter {

    Context mContext;
    StartContract.View mView;
    UserService mUserService;

    public StartPresenter(StartContract.View view) {
        mContext = (Context) view;
        mView = view;
        mUserService = Injection.provideUserService(mContext);
    }

    @Override
    public void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setEmail(firebaseUser.getEmail());
        user.setName(firebaseUser.getDisplayName());

        mUserService.createUser(user);
        SharedPrefUtil.getInstance(mContext).initialize();
    }

    @Override
    public void justStart() {
        SharedPrefUtil.getInstance(mContext).initialize();
    }
}
