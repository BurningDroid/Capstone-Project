package com.youknow.timeisgold.data.source;

import com.youknow.timeisgold.data.User;

/**
 * Created by Aaron on 30/08/2017.
 */

public interface UserDataSource {
    void saveUser(User user);
    void getUser(String id, final OnLoadedUserListener callback);

    interface OnLoadedUserListener {
        void onLoadedUser(User user);
    }
}
