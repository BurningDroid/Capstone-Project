package com.youknow.timeisgold.view.auth;

import com.google.firebase.auth.FirebaseUser;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.User;
import com.youknow.timeisgold.service.UserService;
import com.youknow.timeisgold.utils.SharedPrefUtil;

import android.content.Context;

/**
 * Created by Aaron on 31/08/2017.
 */

public class AuthPresenter implements AuthContract.Presenter {

    Context mContext;
    AuthContract.View mView;
    UserService mUserService;

    public AuthPresenter(AuthContract.View view) {
        mContext = (Context) view;
        mView = view;
        mUserService = Injection.provideUserService(mContext);
    }

    @Override
    public void authenticate(FirebaseUser firebaseUser) {
        final User user = new User();
        user.setEmail(firebaseUser.getEmail());
        user.setName(firebaseUser.getDisplayName());

        mUserService.authenticate(user, new UserService.AuthListner() {
            @Override
            public void done() {
                SharedPrefUtil.getInstance(mContext).initialize();
                mView.authComplete(mContext.getString(R.string.welcome_user, user.getName()));
            }
        });
    }

    @Override
    public void justStart() {
        SharedPrefUtil.getInstance(mContext).initialize();
    }
}
