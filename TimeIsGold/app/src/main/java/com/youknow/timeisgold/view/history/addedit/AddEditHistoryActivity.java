package com.youknow.timeisgold.view.history.addedit;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.History;
import com.youknow.timeisgold.utils.DateTimeUtil;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditHistoryActivity extends AppCompatActivity implements AddEditHistoryContract.View {

    private static final String TAG = AddEditHistoryActivity.class.getSimpleName();

    AddEditHistoryContract.Presenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    ActionBar mActionBar;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.et_desc)
    EditText mEtDesc;
    @BindView(R.id.tv_start_date)
    TextView mTvStartDate;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.tv_end_date)
    TextView mTvEndDate;
    @BindView(R.id.tv_end_time)
    TextView mTvEndTime;
    @BindView(R.id.fab_update_history)
    FloatingActionButton mFabUpdate;

    private History mHistory;
    private Activity mActivity;

    private int sYear, sMonth, sDay, sHour, sMin;
    private int eYear, eMonth, eDay, eHour, eMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_history);

        ButterKnife.bind(this);

        mPresenter = AddEditHistoryPresenter.getInstance(this);
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.key_history))) {
            mHistory = intent.getParcelableExtra(getString(R.string.key_history));
            mPresenter.getActivity(mHistory.getActivityId());
        }

        setView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_delete:
                Log.d(TAG, "[TIG] onOptionsItemSelected - delete history: " + mHistory);
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.delete_history_confirm_message))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.deleteHistory(mHistory);
                            }
                        });
                builder.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onLoadedActivity(Activity activity) {
        mActivity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.tv_start_date)
    public void onClickStartDate() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sYear = year;
                sMonth = month;
                sDay = dayOfMonth;
                mTvStartDate.setText(getString(R.string.date_format, year, String.format("%02d", (month + 1)), String.format("%02d", dayOfMonth)));
            }
        }, DateTimeUtil.getCurrentYear(), DateTimeUtil.getCurrentMonth(), DateTimeUtil.getCurrentDay());
        dialog.show();
    }

    @OnClick(R.id.tv_start_time)
    public void onClickStartTime() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                sHour = hourOfDay;
                sMin = minute;
                mTvStartTime.setText(getString(R.string.time_format_short, String.format("%02d", hourOfDay), String.format("%02d", minute)));
            }
        }, DateTimeUtil.getCurrentHour(), DateTimeUtil.getCurrentMinute(), true);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.tv_end_date)
    public void onClickEndDate() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                eYear = year;
                eMonth = month;
                eDay = dayOfMonth;
                mTvEndDate.setText(getString(R.string.date_format, year, String.format("%02d", (month + 1)), String.format("%02d", dayOfMonth)));
            }
        }, DateTimeUtil.getCurrentYear(), DateTimeUtil.getCurrentMonth(), DateTimeUtil.getCurrentDay());
        dialog.show();
    }

    @OnClick(R.id.tv_end_time)
    public void onClickEndTime() {
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eHour = hourOfDay;
                eMin = minute;
                mTvEndTime.setText(getString(R.string.time_format_short, String.format("%02d", hourOfDay), String.format("%02d", minute)));
            }
        }, DateTimeUtil.getCurrentHour(), DateTimeUtil.getCurrentMinute(), true);
        dialog.show();
    }

    @OnClick(R.id.fab_update_history)
    public void onClickUpdateHistory() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(sYear, sMonth, sDay, sHour, sMin);
        long sTime = calendar.getTimeInMillis();

        calendar.set(eYear, eMonth, eDay, eHour, eMin);
        long eTime = calendar.getTimeInMillis();

        if (sTime > eTime) {
            Toast.makeText(this, getString(R.string.error_incorrect_time), Toast.LENGTH_SHORT).show();
            return;
        }

        mActivity.setDesc(mEtDesc.getText().toString());
        mActivity.setStartTime(sTime);
        mActivity.setEndTime(eTime);
        mActivity.setRelElapsedTime(eTime - sTime);
        mPresenter.saveActivity(mActivity);
    }

    private void setView() {
        mToolbar.setBackgroundColor(mHistory.getColor());
        mIvIcon.setImageResource(mHistory.getIcon());
        mCollapsingToolbarLayout.setTitle(mHistory.getName());
        mCollapsingToolbarLayout.setBackgroundColor(mHistory.getColor());
        mEtDesc.setText(mHistory.getDesc());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mHistory.getStartTime());
        sYear = calendar.get(Calendar.YEAR);
        sMonth = calendar.get(Calendar.MONTH);
        sDay = calendar.get(Calendar.DAY_OF_MONTH);
        sHour = calendar.get(Calendar.HOUR_OF_DAY);
        sMin = calendar.get(Calendar.MINUTE);
        Date startDate = calendar.getTime();

        calendar.setTimeInMillis(mHistory.getEndTime());
        eYear = calendar.get(Calendar.YEAR);
        eMonth = calendar.get(Calendar.MONTH);
        eDay = calendar.get(Calendar.DAY_OF_MONTH);
        eHour = calendar.get(Calendar.HOUR_OF_DAY);
        eMin = calendar.get(Calendar.MINUTE);
        Date endDate = calendar.getTime();

        mTvStartDate.setText(DateTimeUtil.DATE_FORMAT.format(startDate));
        mTvStartTime.setText(DateTimeUtil.TIME_FORMAT_SHORT.format(startDate));
        mTvEndDate.setText(DateTimeUtil.DATE_FORMAT.format(endDate));
        mTvEndTime.setText(DateTimeUtil.TIME_FORMAT_SHORT.format(endDate));
    }

}
