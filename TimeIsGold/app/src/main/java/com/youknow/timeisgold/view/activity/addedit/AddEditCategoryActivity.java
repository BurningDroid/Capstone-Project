package com.youknow.timeisgold.view.activity.addedit;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.data.Type;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditCategoryActivity extends AppCompatActivity implements AddEditCategoryContract.View, ColorDialog.ColorListener, CategoryDialog.CategoryListener {

    private static final String TAG = AddEditCategoryActivity.class.getSimpleName();

    ActionBar mActionBar;
    @BindView(R.id.header)
    LinearLayout mLinearLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_category_name)
    TextView mTvCategoryName;
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

    AddEditCategoryContract.Presenter mPresenter;

    String mName;
    int mColor = 0xff33b5e5;
    int mIcon = R.drawable.ic_category_eating;
    Type mType;

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

        SpinnerAdapter adapter = new ArrayAdapter<Type>(this, R.layout.support_simple_spinner_dropdown_item, Type.values());
        mSpnCategoryType.setAdapter(adapter);
        colorSelected(mColor);
        mEtCategoryName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mTvCategoryName.setText(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
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

        mPresenter.saveCategory(new Category(mName, mColor, mIcon, mType));
    }

    @Override
    public void colorSelected(int color) {
        mColor = color;
        mIvCategoryColor.setColorFilter(color);
        mIvCategoryIconBg.setColorFilter(color);
        mLinearLayout.setBackgroundColor(color);
        mToolbar.setBackgroundColor(color);
    }

    @Override
    public void categorySelected(int icon) {
        mIcon = icon;
        mIvIcon.setImageResource(icon);
        mIvCategoryIcon.setImageResource(icon);
    }

    @Override
    public void finish() {
        super.finish();
        Toast.makeText(this, getString(R.string.category_is_saved), Toast.LENGTH_SHORT).show();
    }

    private void showDialog(DialogFragment dialog) {
        dialog.show(getSupportFragmentManager(), "");
    }

}
