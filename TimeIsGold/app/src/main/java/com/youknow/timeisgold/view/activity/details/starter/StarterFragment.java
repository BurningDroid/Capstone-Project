package com.youknow.timeisgold.view.activity.details.starter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.utils.DateTimeUtil;
import com.youknow.timeisgold.view.activity.details.CategoryDetailsContract;
import com.youknow.timeisgold.view.activity.details.StopActivityDialog;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StarterFragment extends Fragment implements StarterContract.View, StopActivityDialog.StopActivityListener {

    private static final String TAG = StarterFragment.class.getSimpleName();

    @BindView(R.id.et_desc)
    EditText mEtDesc;
    @BindView(R.id.bar_chart)
    BarChart mBarChart;
    @BindView(R.id.fab_operator)
    FloatingActionButton mFabOperator;

    // Running State
    @BindView(R.id.chron_elapsed_time)
    Chronometer mElapsedTime;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;

    CategoryDetailsContract.View mContainer;
    StarterContract.Presenter mPresenter;
    Category mCategory;
    Activity mActivity;

    public StarterFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoryDetailsContract.View) {
            mContainer = (CategoryDetailsContract.View) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = StarterPresenter.getInstance(getContext());
        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_starter, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(getString(R.string.key_category))) {
                mCategory = bundle.getParcelable(getString(R.string.key_category));
            }

            if (bundle.containsKey(getString(R.string.key_activity))) {
                mActivity = bundle.getParcelable(getString(R.string.key_activity));
            }

            if (mActivity != null) {
                if (mActivity.isRunning()) {
                    mPresenter.getCategory(mActivity.getCategoryId());
                    showRunningState(mActivity);
                }
            } else if (mCategory != null) {
                mPresenter.get7dayData(mCategory);
                showReadyState(mCategory);
            }
        }
    }

    @Override
    public void onLoadedCategory(Category category) {
        mCategory = category;
    }

    @Override
    public void onLoadedChartData(final String[] labels, BarData data) {

        if (labels == null || data == null) {
            mBarChart.setVisibility(View.GONE);
            return;
        }

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels[(int) value];
            }

        };

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        mBarChart.getDescription().setEnabled(false);
        mBarChart.setData(data);
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.setFitBars(true);
        mBarChart.invalidate();
    }

    @Override
    public void showRunningState(Activity activity) {
        mActivity = activity;
        mFabOperator.setImageResource(R.drawable.ic_stop);
        mTvStartTime.setVisibility(View.VISIBLE);
        mElapsedTime.setVisibility(View.VISIBLE);
        mBarChart.setVisibility(View.GONE);

        mTvStartTime.setText(getString(R.string.started_at, DateTimeUtil.DATE_TIME_FORMAT.format(new Date(activity.getStartTime()))));
        mElapsedTime.setBase(activity.getRelStartTime());
        mElapsedTime.start();
    }

    @Override
    public void finish() {
        mContainer.finish();
    }

    @Override
    public void stopActivity(Activity activity) {
        mActivity.setDesc(mEtDesc.getText().toString());
        mPresenter.stopActivity(activity);
    }

    @OnClick(R.id.fab_operator)
    public void onClickStart() {
        if (mActivity != null && mActivity.isRunning()) {
            Log.d(TAG, "[TIG] stop the running activity");
            mActivity.setEndTime(System.currentTimeMillis());
            mActivity.setRelEndTime(SystemClock.elapsedRealtime());
            mActivity.setRelElapsedTime(mActivity.getRelEndTime() - mActivity.getRelStartTime());

            StopActivityDialog dialog = new StopActivityDialog();
            dialog.setListener(this);
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.key_activity), mActivity);
            bundle.putParcelable(getString(R.string.key_category), mCategory);
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "");
        } else {
            mPresenter.startActivity(mCategory);
        }
    }

    private void showReadyState(Category category) {
        mFabOperator.setImageResource(R.drawable.ic_start);
        mTvStartTime.setVisibility(View.GONE);
        mElapsedTime.setVisibility(View.GONE);
        mBarChart.setVisibility(View.VISIBLE);
        mElapsedTime.stop();
    }

}
