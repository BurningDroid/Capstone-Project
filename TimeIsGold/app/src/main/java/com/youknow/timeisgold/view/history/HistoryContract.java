package com.youknow.timeisgold.view.history;

import com.youknow.timeisgold.data.History;

import java.util.List;

/**
 * Created by Aaron on 09/09/2017.
 */

public interface HistoryContract {
    interface View {
        void showEmptyHistory();

        void onLoadedHistories(List<History> histories);
    }

    interface Presenter {

        void setView(View view);

        void getAllHistory();

        void clearHistory();

    }
}
