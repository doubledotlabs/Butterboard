package com.doubledotlabs.butterboard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.doubledotlabs.butterboard.R;
import com.doubledotlabs.butterboard.adapters.ListAdapter;
import com.doubledotlabs.butterboard.fragments.MainFragment;
import com.doubledotlabs.butterboard.utils.KeyboardUtils;

public class SettingsActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private static final int REQUEST_INPUT_METHOD = 2367;
    private static final int REQUEST_ACCESSIBILITY = 5824;

    private AppBarLayout appBarLayout;
    private Fragment fragment;
    private View setInputButton, accessibilityButton;

    private GridLayoutManager layoutManager;
    private ListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        AppCompatImageView imageView = (AppCompatImageView) findViewById(R.id.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setInputButton = findViewById(R.id.setInputView);
        accessibilityButton = findViewById(R.id.accessibilityView);

        if (savedInstanceState == null) {
            fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
        } else fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

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

    @Override
    public void onBackStackChanged() {
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        appBarLayout.setExpanded(false, true);
    }

    @Override
    protected void onDestroy() {
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
        super.onDestroy();
    }
}
