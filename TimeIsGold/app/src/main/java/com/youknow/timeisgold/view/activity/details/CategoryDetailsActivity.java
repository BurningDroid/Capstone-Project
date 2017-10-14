package com.youknow.timeisgold.view.activity.details;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Activity;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.view.activity.addedit.AddEditCategoryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.*;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryDetailsActivity extends AppCompatActivity implements CategoryDetailsContract.View {

    private static final String TAG = CategoryDetailsActivity.class.getSimpleName();

    CategoryDetailsContract.Presenter mPresenter;

    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.iv_favorite)
    ImageView mIvFavorite;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    ActionBar mActionBar;
    @BindView(R.id.tab_dots)
    TabLayout mTabLayout;
    @BindView(R.id.activity_viewpager)
    ViewPager mViewPager;

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
                onLoadedCategory(mCategory);
            } else if (intent.hasExtra(getString(R.string.key_activity))) {
                mActivity = intent.getParcelableExtra(getString(R.string.key_activity));
                mPresenter.getCategory(mActivity.getCategoryId());
            }
        }

        PagerAdapter pagerAdapter = new StarterPagerAdapter(this, getSupportFragmentManager(), mCategory, mActivity);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager, true);
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
                return true;
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

    @Override
    public void onLoadedCategory(Category category) {
        if (category == null) {
            return;
        }

        mCategory = category;
        int favorite = category.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_no_favorite;
        mIvFavorite.setImageResource(favorite);
        mIvIcon.setImageResource(category.getIcon());
        mCollapsingToolbar.setTitle(category.getName());
        mCollapsingToolbar.setBackgroundColor(category.getColor());
        mToolbar.setBackgroundColor(category.getColor());
    }

    @Override
    public void deleteDone() {
        Toast.makeText(this, getString(R.string.delete_category_done), Toast.LENGTH_SHORT).show();
        finish();
    }

}
