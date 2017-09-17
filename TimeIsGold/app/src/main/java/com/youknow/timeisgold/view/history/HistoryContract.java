package com.youknow.timeisgold.view.history;

import com.youknow.timeisgold.data.History;

import android.database.Cursor;

import java.util.List;

/**
 * Created by Aaron on 09/09/2017.
 */

public interface HistoryContract {

    interface View {
        void showEmptyHistory();
    }

    interface Presenter {

        void setView(View view);

        void clearHistory();

        List<History> convertToHistory(Cursor data);
    }
}
