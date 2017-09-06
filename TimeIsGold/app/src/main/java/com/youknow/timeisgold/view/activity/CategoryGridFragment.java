package com.youknow.timeisgold.view.activity;


import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;
import com.youknow.timeisgold.view.activity.CategoryAdapter.CategoryListener;
import com.youknow.timeisgold.view.activity.addedit.AddEditCategoryActivity;
import com.youknow.timeisgold.view.activity.details.CategoryDetailsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryGridFragment extends Fragment implements CategoryContract.View, CategoryListener {

    private static final String TAG = CategoryGridFragment.class.getSimpleName();

    @BindView(R.id.fab_add_category)
    FloatingActionButton mFabAddCategory;
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    GridLayoutManager mLayoutManager;
    CategoryAdapter mAdapter;

    private CategoryContract.Presenter mPresenter;

    public CategoryGridFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = CategoryPresenter.getInstance(getContext());
        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "[TIG] onCreateView");
        View root = inflater.inflate(R.layout.fragment_category_grid, container, false);
        ButterKnife.bind(this, root);

        mAdapter = new CategoryAdapter(this);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? mLayoutManager.getSpanCount() : 1;
            }
        });
        mRvCategory.setLayoutManager(mLayoutManager);
        mRvCategory.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        List<Category> categoryList = mPresenter.getAllCategory();
        mAdapter.setCategoryList(categoryList);
    }

    @OnClick(R.id.fab_add_category)
    public void onClickAddCategory() {
        Intent intent = new Intent(getContext(), AddEditCategoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClickCategory(Category category) {
        if (mPresenter.hasRunningActivity()) {
            Toast.makeText(getContext(), getString(R.string.there_is_an_already_running_activity), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
            intent.putExtra(getString(R.string.key_category), category);
            Log.d(TAG, "[TIG] onClickCategory: " + category);
            startActivity(intent);
        }
    }
}
