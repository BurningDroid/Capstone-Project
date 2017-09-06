package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.utils.DateFormatUtil;
import com.youknow.timeisgold.view.activity.addedit.AddEditCategoryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryDetailsActivity extends AppCompatActivity implements CategoryDetailsContract.View, StopActivityDialog.StopActivityListener {

    private static final String TAG = CategoryDetailsActivity.class.getSimpleName();

    CategoryDetailsContract.Presenter mPresenter;

    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;
    @BindView(R.id.iv_favorite)
    ImageView mIvFavorite;
    @BindView(R.id.et_desc)
    EditText mEtDesc;
    @BindView(R.id.fab_operator)
    FloatingActionButton mFabOperator;
    @BindView(R.id.header)
    ConstraintLayout mHeader;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    ActionBar mActionBar;

    // Running State
    @BindView(R.id.chron_elapsed_time)
    Chronometer mElapsedTime;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;

    Category mCategory;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        ButterKnife.bind(this);

        setTitle("");

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
                onLoadedCategory(mCategory);
            } else if (intent.hasExtra(getString(R.string.key_activity))) {
                mActivity = intent.getParcelableExtra(getString(R.string.key_activity));
                if (mActivity.isRunning()) {
                    mPresenter.getCategory(mActivity.getCategoryId());
                    showRunningState(mActivity);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_edit:
                Intent intent = new Intent(this, AddEditCategoryActivity.class);
                intent.putExtra(getString(R.string.key_category), mCategory);
                startActivity(intent);
                finish();
                break;
            case R.id.action_delete:
                mPresenter.deleteCategory(mCategory);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_favorite)
    public void onClickFavorite() {
        if (mCategory.isFavorite()) {
            mCategory.setFavorite(false);
            mIvFavorite.setImageResource(R.drawable.ic_no_favorite);
        } else {
            mCategory.setFavorite(true);
            mIvFavorite.setImageResource(R.drawable.ic_favorite);
        }

        mPresenter.saveCategory(mCategory);
    }

    @OnClick(R.id.fab_operator)
    public void onClickStart() {
        if (mActivity != null && mActivity.isRunning()) {
            Log.d(TAG, "[TIG] stop the running activity");
            mActivity.setEndTime(System.currentTimeMillis());
            mActivity.setRelEndTime(SystemClock.elapsedRealtime());
            mActivity.setRelElapsedTime(mActivity.getRelEndTime() - mActivity.getRelStartTime());

            StopActivityDialog dialog = new StopActivityDialog();
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.key_activity), mActivity);
            bundle.putParcelable(getString(R.string.key_category), mCategory);
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "");
        } else {
            mPresenter.startActivity(mCategory);
        }
    }

    private void showReadyState(Category category) {
        int favorite = category.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_no_favorite;
        mIvFavorite.setImageResource(favorite);
        mFabOperator.setImageResource(R.drawable.ic_start);
        mTvStartTime.setVisibility(View.GONE);
        mElapsedTime.setVisibility(View.GONE);
        mElapsedTime.stop();
    }

    @Override
    public void showRunningState(Activity activity) {
        mActivity = activity;
        mFabOperator.setImageResource(R.drawable.ic_stop);
        mTvStartTime.setVisibility(View.VISIBLE);
        mElapsedTime.setVisibility(View.VISIBLE);

        mTvStartTime.setText(getString(R.string.started_at, DateFormatUtil.DATE_TIME_FORMAT.format(new Date(activity.getStartTime()))));
        mElapsedTime.setBase(activity.getRelStartTime());
        mElapsedTime.start();
    }

    @Override
    public void onLoadedCategory(Category category) {
        mCategory = category;
        int favorite = category.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_no_favorite;
        mIvFavorite.setImageResource(favorite);
        mIvIcon.setImageResource(category.getIcon());
        mTvCategoryName.setText(category.getName());
        mHeader.setBackgroundColor(category.getColor());
        mToolbar.setBackgroundColor(category.getColor());
    }

    @Override
    public void deleteDone() {
        Toast.makeText(this, getString(R.string.delete_category_done), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void stopActivity(Activity activity) {
        mActivity.setDesc(mEtDesc.getText().toString());
        mPresenter.stopActivity(activity);
    }
}
