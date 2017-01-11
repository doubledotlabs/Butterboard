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
import com.doubledotlabs.butterboard.adapters.ListAdapter;
import com.doubledotlabs.butterboard.data.CategoryListData;
import com.doubledotlabs.butterboard.data.ListData;

import java.util.ArrayList;
import java.util.Arrays;

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.setAdapter(new ListAdapter(new ArrayList<ListData>(Arrays.asList(
                new CategoryListData(R.drawable.ic_settings_general, "General", CategoryFragment.CATEGORY_GENERAL),
                new CategoryListData(R.drawable.ic_settings_typing, "Typing", CategoryFragment.CATEGORY_TYPING),
                new CategoryListData(R.drawable.ic_settings_layout, "Layout", CategoryFragment.CATEGORY_LAYOUT),
                new CategoryListData(R.drawable.ic_settings_appearance, "Appearance", CategoryFragment.CATEGORY_APPEARANCE),
                new CategoryListData(R.drawable.ic_settings_spellcheck, "Spellcheck", CategoryFragment.CATEGORY_SPELLCHECK)
        ))));

        return v;
    }
}
