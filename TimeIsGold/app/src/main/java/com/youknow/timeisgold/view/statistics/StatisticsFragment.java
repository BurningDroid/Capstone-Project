package com.youknow.timeisgold.view.statistics;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.youknow.timeisgold.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    StatisticsContract.Presenter mPresenter;

    @BindView(R.id.tv_label)
    TextView mTvLabel;
    @BindView(R.id.pie_chart)
    PieChart mPieChart;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = StatisticsPresenter.getInstance(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null && args.containsKey(getString(R.string.key_statistics_day))) {
            int day = args.getInt(getString(R.string.key_statistics_day));
            mPresenter.getActivities(day);
        }
    }
}
