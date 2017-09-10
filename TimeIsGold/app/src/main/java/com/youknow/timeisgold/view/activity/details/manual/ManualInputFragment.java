package com.youknow.timeisgold.view.activity.details.manual;


import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.utils.DateTimeUtil;
import com.youknow.timeisgold.view.activity.details.CategoryDetailsContract;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManualInputFragment extends Fragment implements ManualInputContract.View {

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
    @BindView(R.id.fab_add_activity)
    FloatingActionButton mFabAddActivity;

    ManualInputContract.Presenter mPresenter;
    Category mCategory;

    String defaultDate;
    String defaultTime;

    int sYear;
    int sMonth;
    int sDay;
    int sHour;
    int sMin;

    int eYear;
    int eMonth;
    int eDay;
    int eHour;
    int eMin;
    private CategoryDetailsContract.View mContainer;

    public ManualInputFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoryDetailsContract.View) {
            mContainer = (CategoryDetailsContract.View) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ManualInputPresenter.getInstance(getContext());
        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manual_input, container, false);
        ButterKnife.bind(this, root);

        Date currentDate = new Date(System.currentTimeMillis());
        defaultDate = DateTimeUtil.DATE_FORMAT.format(currentDate);
        defaultTime = DateTimeUtil.TIME_FORMAT_SHORT.format(currentDate);
        mTvStartDate.setText(defaultDate);
        mTvStartTime.setText(defaultTime);
        mTvEndDate.setText(defaultDate);
        mTvEndTime.setText(defaultTime);

        sYear = eYear = DateTimeUtil.getCurrentYear();
        sMonth = eMonth = DateTimeUtil.getCurrentMonth();
        sDay = eDay = DateTimeUtil.getCurrentDay();
        sHour = eHour = DateTimeUtil.getCurrentHour();
        sMin = eMin = DateTimeUtil.getCurrentMinute();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null && args.containsKey(getString(R.string.key_category))) {
            mCategory = args.getParcelable(getString(R.string.key_category));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.tv_start_date)
    public void onClickStartDate() {
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sYear = year;
                sMonth = month;
                sDay = dayOfMonth;
                mTvStartDate.setText(year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth));
            }
        }, DateTimeUtil.getCurrentYear(), DateTimeUtil.getCurrentMonth(), DateTimeUtil.getCurrentDay());
        dialog.show();
    }

    @OnClick(R.id.tv_start_time)
    public void onClickStartTime() {
        TimePickerDialog dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                sHour = hourOfDay;
                sMin = minute;
                mTvStartTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
            }
        }, DateTimeUtil.getCurrentHour(), DateTimeUtil.getCurrentMinute(), true);
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick(R.id.tv_end_date)
    public void onClickEndDate() {
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                eYear = year;
                eMonth = month;
                eDay = dayOfMonth;
                mTvEndDate.setText(year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth));
            }
        }, DateTimeUtil.getCurrentYear(), DateTimeUtil.getCurrentMonth(), DateTimeUtil.getCurrentDay());
        dialog.show();
    }

    @OnClick(R.id.tv_end_time)
    public void onClickEndTime() {
        TimePickerDialog dialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                eHour = hourOfDay;
                eMin = minute;
                mTvEndTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
            }
        }, DateTimeUtil.getCurrentHour(), DateTimeUtil.getCurrentMinute(), true);
        dialog.show();
    }

    @OnClick(R.id.fab_add_activity)
    public void onClickAddActivity() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(sYear, sMonth, sDay, sHour, sMin);
        long sTime = calendar.getTimeInMillis();

        calendar.set(eYear, eMonth, eDay, eHour, eMin);
        long eTime = calendar.getTimeInMillis();

        if (sTime > eTime) {
            Toast.makeText(getContext(), getString(R.string.error_incorrect_time), Toast.LENGTH_SHORT).show();
            return;
        }

        Activity activity = new Activity();
        activity.setCategoryId(mCategory.getId());
        activity.setDesc(mEtDesc.getText().toString());
        activity.setStartTime(sTime);
        activity.setEndTime(eTime);
        activity.setRelElapsedTime(eTime - sTime);
        Toast.makeText(getContext(), "Elapsed Time: " + DateTimeUtil.getElapsedTime(eTime - sTime), Toast.LENGTH_SHORT).show();
        mPresenter.createNewActivity(activity);
    }

    @Override
    public void finish() {
        mContainer.finish();
    }
}
