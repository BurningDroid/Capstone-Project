package com.youknow.timeisgold.view.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryGridFragment extends Fragment implements CategoryContract.View {

    private static final String TAG = CategoryGridFragment.class.getSimpleName();
    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    GridLayoutManager mLayoutManager;
    CategoryAdapter mAdapter;

    private CategoryContract.Presenter mPresenter;

    public CategoryGridFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_grid, container, false);
        ButterKnife.bind(this, root);

        mAdapter = new CategoryAdapter();
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
        Log.d(TAG, "[TIG] categoryList.size: " + categoryList.size());
        mAdapter.setCategoryList(categoryList);
    }

    public void setPresenter(CategoryContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.setView(this);
    }
}
