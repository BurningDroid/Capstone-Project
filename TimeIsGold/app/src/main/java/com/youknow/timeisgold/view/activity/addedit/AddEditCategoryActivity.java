package com.youknow.timeisgold.view.activity.addedit;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.Type;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditCategoryActivity extends AppCompatActivity implements AddEditCategoryContract.View, ColorDialog.ColorListener, CategoryDialog.CategoryListener {

    private static final String TAG = AddEditCategoryActivity.class.getSimpleName();

    ActionBar mActionBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.et_category_name)
    EditText mEtCategoryName;
    @BindView(R.id.iv_category_color)
    ImageView mIvCategoryColor;
    @BindView(R.id.iv_category_icon)
    ImageView mIvCategoryIcon;
    @BindView(R.id.iv_category_icon_bg)
    ImageView mIvCategoryIconBg;
    @BindView(R.id.spn_category_type)
    Spinner mSpnCategoryType;
    @BindView(R.id.fab_save_category)
    FloatingActionButton mFabSaveCategory;

    ArrayAdapter mAdapter;
    AddEditCategoryContract.Presenter mPresenter;

    String mName;
    int mColor = 0xff33b5e5;
    int mIcon = R.drawable.ic_category_eating;
    Type mType;
    Category mCategory = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_category);

        ButterKnife.bind(this);
        mPresenter = AddEditCategoryPresenter.getInstance();
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mAdapter = new ArrayAdapter<Type>(this, R.layout.support_simple_spinner_dropdown_item, Type.values());
        mSpnCategoryType.setAdapter(mAdapter);
        colorSelected(mColor);
        mEtCategoryName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mCollapsingToolbar.setTitle(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.key_category))) {
            mCategory = intent.getParcelableExtra(getString(R.string.key_category));
            showEditView();
        }
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
                mPresenter.deleteCategory(mCategory);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_category_color)
    public void onClickCategoryColor() {
        showDialog(new ColorDialog());
    }

    @OnClick(R.id.iv_category_icon)
    public void onClickCategoryIcon() {
        showDialog(new CategoryDialog());
    }

    @OnClick(R.id.fab_save_category)
    public void onClickSaveCategory() {
        mName = mEtCategoryName.getText().toString();
        mType = (Type) mSpnCategoryType.getSelectedItem();

        if (mCategory == null) {
            mCategory = new Category(System.currentTimeMillis(), mName, mColor, mIcon, mType);
            mPresenter.createCategory(mCategory);
        } else {
            mCategory.setName(mName);
            mCategory.setColor(mColor);
            mCategory.setIcon(mIcon);
            mCategory.setType(mType);
            mPresenter.updateCategory(mCategory);
        }
    }

    @Override
    public void colorSelected(int color) {
        mColor = color;
        mCollapsingToolbar.setBackgroundColor(color);
        mIvCategoryColor.setColorFilter(color);
        mIvCategoryIconBg.setColorFilter(color);
        mToolbar.setBackgroundColor(color);
    }

    @Override
    public void categorySelected(int icon) {
        mIcon = icon;
        mIvIcon.setImageResource(icon);
        mIvCategoryIcon.setImageResource(icon);
    }

    @Override
    public void finish(String message) {
        super.finish();
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog(DialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), "");
    }

    private void showEditView() {
        setTitle(getString(R.string.action_edit));
        categorySelected(mCategory.getIcon());
        colorSelected(mCategory.getColor());
        mEtCategoryName.setText(mCategory.getName());
        mCollapsingToolbar.setTitle(mCategory.getName());
        mSpnCategoryType.setSelection(mAdapter.getPosition(mCategory.getType()));
    }
}
