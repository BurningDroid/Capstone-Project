package com.youknow.timeisgold.view.statistics;

/**
 * Created by Aaron on 11/09/2017.
 */

public interface StatisticsContract {
    interface View {

    }

    interface Presenter {

        void getActivities(int day);
    }
}
