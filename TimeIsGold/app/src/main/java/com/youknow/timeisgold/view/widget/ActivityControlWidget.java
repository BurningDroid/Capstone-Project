package com.youknow.timeisgold.view.widget;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.source.ActivityDataSource;
import com.youknow.timeisgold.data.source.CategoryDataSource;
import com.youknow.timeisgold.view.MainActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class ActivityControlWidget extends AppWidgetProvider {

    private static final String TAG = ActivityControlWidget.class.getSimpleName();
    private static final long DEFAULT_INVALID_CATEGORY_ID = -100;

    private static CategoryDataSource mCategoryDataSource;
    private static ActivityDataSource mActivityDataSource;
    private static Activity mRunningActivity = null;

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.pref_widget), Context.MODE_PRIVATE);
        final long categoryId = pref.getLong(String.valueOf(appWidgetId), DEFAULT_INVALID_CATEGORY_ID);

        if (categoryId != DEFAULT_INVALID_CATEGORY_ID) {
            if (mCategoryDataSource == null) {
                mCategoryDataSource = Injection.provideCategoryDataSource(context);
            }

            Category category = mCategoryDataSource.getCategory(categoryId);
            Log.d(TAG, "[TIG][widget] updateAppWidget - category: " + category);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity_control);
            views.setInt(R.id.widget_container, "setBackgroundColor", category.getColor());
            views.setImageViewResource(R.id.iv_icon, category.getIcon());
            views.setTextViewText(R.id.tv_category_name, category.getName());
            views.setImageViewResource(R.id.iv_control, R.drawable.ic_circle_start);

            Intent receiverIntent = new Intent(context, WidgetReceiver.class);
            receiverIntent.putExtra(context.getString(R.string.key_category), category);
            receiverIntent.putExtra(context.getString(R.string.key_widget_id), appWidgetId);
            PendingIntent receiverPi = PendingIntent.getBroadcast(context, (int) category.getId(), receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.iv_control, receiverPi);

            Intent activityIntent = new Intent(context, MainActivity.class);
            PendingIntent activityPi = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.tv_category_name, activityPi);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (mActivityDataSource == null) {
            mActivityDataSource = Injection.provideLocalActivityDataSource(context);
        }
        mRunningActivity = mActivityDataSource.getRunningActivity();
        Log.d(TAG, "[TIG][widget] onUpdate - runningActivity: " + mRunningActivity);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG, "[TIG][widget] onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "[TIG][widget] onDisable");
    }
}

