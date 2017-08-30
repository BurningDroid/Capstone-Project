package com.youknow.timeisgold.data.source.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.youknow.timeisgold.MainApplication;
import com.youknow.timeisgold.data.User;
import com.youknow.timeisgold.data.source.UserDataSource;

/**
 * Created by Aaron on 30/08/2017.
 */

public class FirebaseUserDataSource implements UserDataSource {

    private static final String TAG = FirebaseUserDataSource.class.getSimpleName();
    private static final String USERS = "users";

    private static FirebaseUserDataSource INSTANCE;

    private DatabaseReference mRef;

    private FirebaseUserDataSource() {
        mRef = MainApplication.getDatabase().getReference(USERS);
    }

    public static UserDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseUserDataSource();
        }

        return INSTANCE;
    }

    @Override
    public void saveUser(User user) {
        mRef.child(user.getId()).setValue(user);
    }

    @Override
    public void getUser(String userId, final OnLoadedUserListener callback) {
        mRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = null;
                if (dataSnapshot.exists()) {
                    user = dataSnapshot.getValue(User.class);
                }
                if (callback != null) {
                    callback.onLoadedUser(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
