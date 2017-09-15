package com.youknow.timeisgold.view.statistics;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.youknow.timeisgold.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    StatisticsContract.Presenter mPresenter;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_header)
    TextView mTvHeader;
    @BindView(R.id.pie_chart)
    PieChart mPieChart;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;

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
        mPresenter.setView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null && args.containsKey(getString(R.string.key_statistics_day))) {
            int day = args.getInt(getString(R.string.key_statistics_day));
            mProgressBar.setVisibility(View.VISIBLE);
            mPresenter.getActivities(day);

            if (day == 0) {
                mTvHeader.setText(R.string.statistics_header_today);
            } else {
                mTvHeader.setText(getString(R.string.statistics_header_past, day));
            }
        }
    }

    @Override
    public void onLoadedChartData(PieData data) {
        mTvHeader.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setEntryLabelColor(R.color.colorPrimaryLight);
        mPieChart.setData(data);
        mPieChart.invalidate();
    }

    @Override
    public void onLoadedEmptyChart() {
        mTvHeader.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mPieChart.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }
}
