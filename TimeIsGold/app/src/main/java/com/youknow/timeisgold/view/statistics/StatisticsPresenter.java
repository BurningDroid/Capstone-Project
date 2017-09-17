package com.youknow.timeisgold.view.statistics;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.youknow.timeisgold.Injection;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.service.ActivityService;
import com.youknow.timeisgold.utils.DateTimeUtil;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Aaron on 11/09/2017.
 */

public class StatisticsPresenter implements StatisticsContract.Presenter {

    private static final String TAG = StatisticsPresenter.class.getSimpleName();
    private static StatisticsPresenter INSTANCE;
    ActivityService mActivityService;

    private Context mContext;
    private StatisticsContract.View mView;

    private StatisticsPresenter(Context context) {
        mContext = context;
        mActivityService = Injection.provideActivityService(mContext);
    }

    public static StatisticsContract.Presenter getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new StatisticsPresenter(context);
        }

        return INSTANCE;
    }

    @Override
    public void setView(StatisticsContract.View view) {
        mView = view;
    }

    @Override
    public void getActivities(int day) {
        Log.d(TAG, "[TIG] getActivities: " + day);
        Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int MM = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(yy, MM, dd, 0, 0, 0);

        day *= -1;
        calendar.add(Calendar.DATE, day);
        long startDate = calendar.getTimeInMillis();

        mActivityService.getHistories(startDate, new ActivityService.OnLoadedHistoriesListener(){

            @Override
            public void onLoadedHistories(List<History> histories) {
                Log.d(TAG, "[TIG] getActivities - size: " + histories.size());
                getChartData(histories);
            }
        });
    }

    private void getChartData(List<History> histories) {
        Map<String, Float> dataMap = new HashMap<>();
        Set<Integer> colorSet = new TreeSet<>();

        for (History history : histories) {
            float elapsedTime = DateTimeUtil.getElapsedHour(history.getElapsedTime());
            if (dataMap.containsKey(history.getName())) {
                elapsedTime += dataMap.get(history.getName());
            }
            colorSet.add(history.getColor());
            dataMap.put(history.getName(), elapsedTime);
        }

        List<PieEntry> entries = new ArrayList<>();
        for (String name : dataMap.keySet()) {
            entries.add(new PieEntry(dataMap.get(name), name));
        }

        if (entries.isEmpty()) {
            mView.onLoadedEmptyChart();
        } else {
            PieDataSet set = new PieDataSet(entries, "(Unit: Hour)");
            set.setValueTextSize(14f);
            set.setValueTextColor(R.color.colorPrimaryLight);
            set.setColors(Arrays.asList(colorSet.toArray(new Integer[colorSet.size()])));
            PieData data = new PieData(set);
            mView.onLoadedChartData(data);
        }

    }
}
