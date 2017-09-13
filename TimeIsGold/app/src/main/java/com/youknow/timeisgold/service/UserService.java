package com.youknow.timeisgold.service;

import com.youknow.timeisgold.data.User;
import com.youknow.timeisgold.data.source.UserDataSource;

/**
 * Created by Aaron on 30/08/2017.
 */

public interface UserService {
    boolean isAuthenticated();

    void authenticate(User user, AuthListner callback);

    void signOut();

    void getUser(String id, UserDataSource.OnLoadedUserListener callback);

    String getUserId();

    interface AuthListner {
        void done();
    }
}
