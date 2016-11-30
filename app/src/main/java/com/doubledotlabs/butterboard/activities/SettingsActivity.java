package com.doubledotlabs.butterboard.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.doubledotlabs.butterboard.R;
import com.doubledotlabs.butterboard.adapters.CategoryAdapter;

public class SettingsActivity extends AppCompatActivity {

    private static final int REQUEST_INPUT_METHOD = 2367;
    private static final int REQUEST_ACCESSIBILITY = 5824;

    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppCompatImageView imageView;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private GridLayoutManager layoutManager;
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        imageView = (AppCompatImageView) findViewById(R.id.imageView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        findViewById(R.id.setInputView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS), REQUEST_INPUT_METHOD);
            }
        });

        findViewById(R.id.accessibilityView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), REQUEST_ACCESSIBILITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_INPUT_METHOD:
                InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (mgr != null) mgr.showInputMethodPicker(); //TODO: ADD AN IF STATEMENT
                break;
            case REQUEST_ACCESSIBILITY:
                break;
        }
    }
}
