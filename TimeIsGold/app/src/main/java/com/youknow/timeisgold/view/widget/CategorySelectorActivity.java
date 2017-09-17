package com.youknow.timeisgold.view.widget;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.view.activity.CategoryAdapter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategorySelectorActivity extends AppCompatActivity implements CategorySelectorContract.View, CategoryAdapter.CategoryListener {

    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    GridLayoutManager mLayoutManager;
    CategoryAdapter mAdapter;
    CategorySelectorContract.Presenter mPresenter;
    private Parcelable mLayoutManagerSavedState;
    private int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);
        ButterKnife.bind(this);

        mPresenter = CategorySelectorPresenter.getInstance(this);
        mPresenter.setView(this);

        mAdapter = new CategoryAdapter(this);
        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? mLayoutManager.getSpanCount() : 1;
            }
        });
        mRvCategory.setLayoutManager(mLayoutManager);
        mRvCategory.setAdapter(mAdapter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getAllCategory();
    }

    @Override
    public void loadCategories(List<Category> categories) {
        mAdapter.setCategoryList(categories);
    }

    @Override
    public void onClickCategory(Category category) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_activity_control);
        views.setInt(R.id.widget_container, "setBackgroundColor", category.getColor());
        views.setImageViewResource(R.id.iv_icon, category.getIcon());
        views.setTextViewText(R.id.collapsing_toolbar, category.getName());
        views.setImageViewResource(R.id.iv_control, R.drawable.ic_circle_start);
        Intent intent = new Intent(this, WidgetReceiver.class);
        intent.putExtra(getString(R.string.key_widget_id), mAppWidgetId);
        intent.putExtra(getString(R.string.key_category), category);
        PendingIntent pe = PendingIntent.getBroadcast(this, (int) category.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.iv_control, pe);

        appWidgetManager.updateAppWidget(mAppWidgetId, views);

        SharedPreferences pref = getSharedPreferences(getString(R.string.pref_widget), MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(String.valueOf(mAppWidgetId), category.getId());
        editor.apply();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
