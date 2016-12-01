package com.doubledotlabs.butterboard.utils;


import android.app.ActivityManager;
import android.content.Context;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import com.doubledotlabs.butterboard.services.AccessibilityService;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class KeyboardUtils {

    public static boolean isEnabled(Context context) {
        for (InputMethodInfo method : ((InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE)).getEnabledInputMethodList()) {
            if (method.getPackageName().equals(context.getApplicationContext().getPackageName()))
                return true;
        }

        return false;
    }

    public static boolean isAccessibilityEnabled(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AccessibilityService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        return false;
    }

}
