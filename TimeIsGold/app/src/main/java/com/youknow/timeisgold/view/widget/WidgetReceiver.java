package com.youknow.timeisgold.view.widget;

import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.service.ActivityService;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetReceiver extends BroadcastReceiver {

    private static final String TAG = WidgetReceiver.class.getSimpleName();

    private ActivityService mActivityService;

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG, "[TIG][receiver] onReceive");
        mActivityService = Injection.provideActivityService(context);

        final Category category = intent.getParcelableExtra(context.getString(R.string.key_category));
        final int widgetId = intent.getIntExtra(context.getString(R.string.key_widget_id), -1);
        if (widgetId == -1) {
            Log.e(TAG, "[TIG][receiver] onReceive - widget id is -1");
            return;
        }

        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity_control);
        views.setInt(R.id.widget_container, "setBackgroundColor", category.getColor());
        views.setImageViewResource(R.id.iv_icon, category.getIcon());
        views.setTextViewText(R.id.tv_category_name, category.getName());

        mActivityService.getRunningActivity(new ActivityService.OnLoadedActivityListener() {
            @Override
            public void onLoadedActivity(Activity activity) {
                if (activity == null) {
                    views.setImageViewResource(R.id.iv_control, R.drawable.ic_circle_pause);
                    Intent intent = new Intent(context, WidgetReceiver.class);
                    intent.putExtra(context.getString(R.string.key_widget_id), widgetId);
                    intent.putExtra(context.getString(R.string.key_category), category);
                    PendingIntent pe = PendingIntent.getBroadcast(context, (int) category.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    views.setOnClickPendingIntent(R.id.iv_control, pe);
                    startNewActivity(category.getId());
                    appWidgetManager.updateAppWidget(widgetId, views);
                } else if (activity.getCategoryId() == category.getId()) {
                    views.setImageViewResource(R.id.iv_control, R.drawable.ic_circle_start);
                    Intent intent = new Intent(context, WidgetReceiver.class);
                    intent.putExtra(context.getString(R.string.key_widget_id), widgetId);
                    intent.putExtra(context.getString(R.string.key_category), category);
                    PendingIntent pe = PendingIntent.getBroadcast(context, (int) category.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    views.setOnClickPendingIntent(R.id.iv_control, pe);
                    stopActivity(activity);
                    appWidgetManager.updateAppWidget(widgetId, views);
                } else {
                    Log.d(TAG, "[TIG][receiver] nothing to do");
                }
            }
        });
    }

    private void startNewActivity(long categoryId) {
        Activity activity = new Activity();
        activity.setCategoryId(categoryId);
        activity.setDesc("");
        activity.setRunning(true);
        activity.setStartTime(System.currentTimeMillis());
        activity.setRelStartTime(SystemClock.elapsedRealtime());

        mActivityService.createActivity(activity);
        Log.d(TAG, "[TIG][receiver] start activity: " + activity);
    }

    private void stopActivity(Activity activity) {
        activity.setEndTime(System.currentTimeMillis());
        activity.setRelEndTime(SystemClock.elapsedRealtime());
        activity.setRelElapsedTime(activity.getRelEndTime() - activity.getRelStartTime());
        activity.setRunning(false);
        mActivityService.updateActivity(activity);
        Log.d(TAG, "[TIG][receiver] stop activity: " + activity);
    }
}
