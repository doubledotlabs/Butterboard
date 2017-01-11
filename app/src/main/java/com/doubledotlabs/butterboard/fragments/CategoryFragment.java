package com.doubledotlabs.butterboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubledotlabs.butterboard.R;

public class CategoryFragment extends Fragment {

    public static final int CATEGORY_GENERAL = 1;
    public static final int CATEGORY_TYPING = 2;
    public static final int CATEGORY_LAYOUT = 3;
    public static final int CATEGORY_APPEARANCE = 4;
    public static final int CATEGORY_SPELLCHECK = 5;

    private static final String EXTRA_CATEGORY = "category";

    private int category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        category = getArguments().getInt(EXTRA_CATEGORY, CATEGORY_GENERAL);

        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        return v;
    }

    public static CategoryFragment getInstance(int category) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_CATEGORY, category);

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
