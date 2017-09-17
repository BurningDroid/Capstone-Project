package com.youknow.timeisgold.view.activity;

import com.youknow.timeisgold.R;
import com.youknow.timeisgold.data.Category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 02/09/2017.
 */

public class CategoryAdapter extends android.support.v7.widget.RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Category> mCategoryList = new ArrayList<>();
    private CategoryListener mListener;

    public CategoryAdapter(CategoryListener categoryListener) {
        mListener = categoryListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = (viewType == TYPE_HEADER) ? R.layout.category_header : R.layout.category_grid_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!isHeader(position)) {
            Category category = mCategoryList.get(position);
            holder.category = category;
            holder.bg.setBackgroundColor(category.getColor());
            holder.ivIcon.setImageResource(category.getIcon());
            holder.tvName.setText(category.getName());
            holder.tvType.setText(category.getType().name());
            int visibility = category.isFavorite() ? View.VISIBLE : View.GONE;
            holder.ivFavorite.setVisibility(visibility);
        }
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    private boolean isHeader(int position) {
        return position == 0;
    }

    public void setCategoryList(List<Category> categoryList) {
        mCategoryList.clear();
        mCategoryList.add(new Category());
        mCategoryList.addAll(categoryList);
        notifyDataSetChanged();
    }

    public interface CategoryListener {
        void onClickCategory(Category category);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Category category;
        View bg;
        ImageView ivIcon;
        TextView tvName;
        TextView tvType;
        ImageView ivFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            bg = itemView;
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_category_icon);
            tvName = (TextView) itemView.findViewById(R.id.tv_category_name);
            tvType = (TextView) itemView.findViewById(R.id.tv_category_type);
            ivFavorite = (ImageView) itemView.findViewById(R.id.iv_favorite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClickCategory(category);
                }
            });
        }
    }
}
