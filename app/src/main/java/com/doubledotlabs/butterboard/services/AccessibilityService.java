package com.doubledotlabs.butterboard.services;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import com.doubledotlabs.butterboard.Butterboard;
import com.doubledotlabs.butterboard.utils.ColorUtils;

public class AccessibilityService extends android.accessibilityservice.AccessibilityService {

    private PackageManager packageManager;
    private Butterboard butterboard;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        packageManager = getPackageManager();
        butterboard = (Butterboard) getApplicationContext();

        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        setServiceInfo(config);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (butterboard != null && packageManager != null && event.getPackageName() != null && event.getPackageName().toString().equals(butterboard.getPackageName())) {
            int color = Color.BLACK;

            try {
                ColorUtils.getPrimaryColor(this, new ComponentName(event.getPackageName().toString(), event.getClassName().toString()));
            } catch (Exception ignored) {
            }

            butterboard.setColor(color);
        }
    }

    @Override
    public void onInterrupt() {
    }
}