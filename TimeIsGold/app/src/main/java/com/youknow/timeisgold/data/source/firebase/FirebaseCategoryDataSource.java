package com.youknow.timeisgold.data.source.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import com.youknow.timeisgold.MainApplication;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.service.CategoryService;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aaron on 14/09/2017.
 */

public class FirebaseCategoryDataSource {
    private static final String TAG = "FirebaseCategoryDataSou";
    private static final String CATEGORIES = "categories";

    private static FirebaseCategoryDataSource INSTANCE;

    private DatabaseReference mRef;

    private FirebaseCategoryDataSource() {
        mRef = MainApplication.getDatabase().getReference(CATEGORIES);
    }

    public static FirebaseCategoryDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseCategoryDataSource();
        }

        return INSTANCE;
    }

    public void saveCategory(String userId, Category category) {
        Log.d(TAG, "[TIG][datasource][remote] saveCategory: " + category);
        mRef.child(userId).child(String.valueOf(category.getId())).setValue(category);
    }

    public void getAllCategory(String userId, final CategoryService.OnLoadedCategoriesListener callback) {
        mRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> categories = new ArrayList();
                if (dataSnapshot.exists()) {
                    Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                    while (it.hasNext()) {
                        Category category = it.next().getValue(Category.class);
                        categories.add(category);
                    }
                }

                if (callback != null) {
                    Log.d(TAG, "[TIG][datasource][remote] getAllCategory - size: " + categories.size());
                    callback.onLoadedCategories(categories);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void deleteCategory(String userId, Category category) {
        Log.d(TAG, "[TIG][datasource][remote] deleteCategory: " + category);
        mRef.child(userId).child(String.valueOf(category.getId())).setValue(category);
    }

}
