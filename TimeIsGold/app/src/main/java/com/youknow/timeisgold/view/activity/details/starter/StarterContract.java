package com.youknow.timeisgold.view.activity.details.starter;

import com.github.mikephil.charting.data.BarData;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;

/**
 * Created by Aaron on 10/09/2017.
 */

public interface StarterContract {
    interface View {

        void onLoadedCategory(Category category);

        void onLoadedChartData(String[] labels, BarData data);

        void showRunningState(Activity activity);

        void finish();

    }

    interface Presenter {

        void setView(View view);

        void get7dayData(Category category);

        void startActivity(Category category);

        void stopActivity(Activity activity);

        void getCategory(long categoryId);

    }
}
