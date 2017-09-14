package com.youknow.timeisgold.data.source.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.youknow.timeisgold.MainApplication;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.service.ActivityService;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aaron on 12/09/2017.
 */

public class FirebaseActivityDataSource {

    private static final String TAG = "FirebaseActivityDataSou";
    private static final String ACTIVITIES = "activities";

    private static FirebaseActivityDataSource INSTANCE;

    private DatabaseReference mRef;

    private FirebaseActivityDataSource() {
        mRef = MainApplication.getDatabase().getReference(ACTIVITIES);
    }

    public static FirebaseActivityDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseActivityDataSource();
        }

        return INSTANCE;
    }

    public void saveActivity(String userId, Activity activity) {
        Log.d(TAG, "[TIG][datasource][remote] saveActivity - " + activity.getId());
        mRef.child(userId).child(String.valueOf(activity.getId())).setValue(activity);
    }

    public void deleteActivity(String userId, long activityId) {
        Log.d(TAG, "[TIG][datasource][remote] deleteActivity: " + activityId);
        mRef.child(userId).child(String.valueOf(activityId)).removeValue();
    }

    public void deleteActivities(String userId) {
        Log.d(TAG, "[TIG][datasource][remote] deleteActivities");
        mRef.child(userId).removeValue();
    }

    public void getAllActivity(String userId, final ActivityService.OnLoadedActivitiesListener callback) {
        mRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Activity> activities = new ArrayList();
                if (dataSnapshot.exists()) {
                    Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                    while (it.hasNext()) {
                        Activity activity = it.next().getValue(Activity.class);
                        activities.add(activity);
                    }
                }

                if (callback != null) {
                    Log.d(TAG, "[TIG][datasource][remote] getAllActivity - size: " + activities.size());
                    callback.onLoadedActivities(activities);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
