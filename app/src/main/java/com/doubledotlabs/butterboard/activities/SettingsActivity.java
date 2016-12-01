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

import com.doubledotlabs.butterboard.R;
import com.doubledotlabs.butterboard.adapters.CategoryAdapter;
import com.doubledotlabs.butterboard.utils.KeyboardUtils;

public class SettingsActivity extends AppCompatActivity {

    private static final int REQUEST_INPUT_METHOD = 2367;
    private static final int REQUEST_ACCESSIBILITY = 5824;

    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppCompatImageView imageView;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private View setInputButton, accessibilityButton;

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
        setInputButton = findViewById(R.id.setInputView);
        accessibilityButton = findViewById(R.id.accessibilityView);

        layoutManager = new GridLayoutManager(this, getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ? 3 : 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        setInputButton.setVisibility(KeyboardUtils.isEnabled(this) ? View.GONE : View.VISIBLE);
        setInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS), REQUEST_INPUT_METHOD);
            }
        });

        accessibilityButton.setVisibility(KeyboardUtils.isAccessibilityEnabled(this) ? View.GONE : View.VISIBLE);
        accessibilityButton.setOnClickListener(new View.OnClickListener() {
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
                setInputButton.setVisibility(KeyboardUtils.isEnabled(this) ? View.GONE : View.VISIBLE);
                break;
            case REQUEST_ACCESSIBILITY:
                accessibilityButton.setVisibility(KeyboardUtils.isAccessibilityEnabled(this) ? View.GONE : View.VISIBLE);
                break;
        }
    }
}
