package com.youknow.timeisgold.view.start;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Aaron on 31/08/2017.
 */

public interface StartContract {

    interface View {

    }

    interface Presenter {
        void createUser(FirebaseUser user);

        void justStart();
    }
}
