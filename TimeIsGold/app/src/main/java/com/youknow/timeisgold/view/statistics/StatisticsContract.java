package com.youknow.timeisgold.view.statistics;

import com.github.mikephil.charting.data.PieData;

/**
 * Created by Aaron on 11/09/2017.
 */

public interface StatisticsContract {
    interface View {

        void onLoadedChartData(PieData data);

        void onLoadedEmptyChart();
    }

    interface Presenter {

        void setView(StatisticsContract.View view);

        void getActivities(int day);

    }
}
