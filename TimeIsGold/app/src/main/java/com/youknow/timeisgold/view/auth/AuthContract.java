package com.youknow.timeisgold.view.auth;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Aaron on 31/08/2017.
 */

public interface AuthContract {

    interface View {

        void authComplete(String message);
    }

    interface Presenter {
        void authenticate(FirebaseUser user);

        void justStart();
    }
}
