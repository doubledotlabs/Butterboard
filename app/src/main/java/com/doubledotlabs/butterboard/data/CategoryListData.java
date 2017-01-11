package com.doubledotlabs.butterboard.data;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doubledotlabs.butterboard.R;
import com.doubledotlabs.butterboard.fragments.CategoryFragment;

public class CategoryListData extends ListData<CategoryListData.ViewHolder> {

    private String text;
    private int resource, fragment;

    public CategoryListData(int resource, String text, int fragment) {
        this.resource = resource;
        this.text = text;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_category, parent, false), this);
    }

    @Override
    public void bindView(ViewHolder holder) {
        holder.imageView.setImageResource(resource);
        holder.textView.setText(text);
    }

    static class ViewHolder extends ListData.ViewHolder implements View.OnClickListener {

        private CategoryListData listData;

        private AppCompatImageView imageView;
        private TextView textView;

        ViewHolder(View itemView, CategoryListData listData) {
            super(itemView);
            imageView = (AppCompatImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);

            this.listData = listData;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, CategoryFragment.getInstance(listData.fragment)).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
        }
    }
}
