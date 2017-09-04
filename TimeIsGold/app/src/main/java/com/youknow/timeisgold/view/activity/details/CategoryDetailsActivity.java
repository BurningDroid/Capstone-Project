package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryDetailsActivity extends AppCompatActivity implements CategoryDetailsContract.View {

    CategoryDetailsContract.Presenter mPresenter;

    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;
    @BindView(R.id.et_desc)
    EditText mEtDesc;
    @BindView(R.id.fab_operator)
    FloatingActionButton mFabOperator;
    @BindView(R.id.header)
    LinearLayout mHeader;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    ActionBar mActionBar;

    // Running State
    @BindView(R.id.tv_spend_time)
    TextView mTvSpendTime;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;

    Category mCategory;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        ButterKnife.bind(this);

        mPresenter = CategoryDetailsPresenter.getInstance(this);
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(getString(R.string.key_category))) {
                mCategory = intent.getParcelableExtra(getString(R.string.key_category));
                showReadyState(mCategory);
            } else if (intent.hasExtra(getString(R.string.key_activity))) {
                mActivity = intent.getParcelableExtra(getString(R.string.key_activity));
                if (mActivity.isRunning()) {
                    mPresenter.getCategory(mActivity.getCategoryId());
                }
            }
        }
    }

    @OnClick(R.id.fab_operator)
    public void onClickStart() {
        if (mActivity != null && mActivity.isRunning()) {
            mActivity.setDesc(mEtDesc.getText().toString());
            mPresenter.stopActivity(mActivity);
        } else {
            mPresenter.startActivity(mCategory);
        }
    }

    private void showReadyState(Category category) {
        mFabOperator.setImageResource(R.drawable.ic_start);
        mTvStartTime.setVisibility(View.GONE);
        mTvSpendTime.setVisibility(View.GONE);
    }

    @Override
    public void showRunningState(Category category, long startTime) {
        mFabOperator.setImageResource(R.drawable.ic_stop);
        mTvStartTime.setVisibility(View.VISIBLE);
        mTvSpendTime.setVisibility(View.VISIBLE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        mTvStartTime.setText(String.format(getString(R.string.started_at), sdf.format(new Date(startTime))));
    }

    @Override
    public void onLoadedCategory(Category category) {
        mIvIcon.setImageResource(category.getIcon());
        mTvCategoryName.setText(category.getName());
        mHeader.setBackgroundColor(category.getColor());
        mToolbar.setBackgroundColor(category.getColor());
    }
}
