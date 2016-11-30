package com.doubledotlabs.butterboard;

import android.app.Application;
import android.support.annotation.ColorInt;

import com.doubledotlabs.butterboard.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;

public class Butterboard extends Application {

    private List<OnColorChangeListener> listeners;

    @ColorInt
    private int color;

    @Override
    public void onCreate() {
        super.onCreate();
        listeners = new ArrayList<>();
        color = ColorUtils.getDefaultColor(this);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        for (OnColorChangeListener listener : listeners) {
            listener.onColorChanged(color);
        }
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    public void addListener(OnColorChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OnColorChangeListener listener) {
        listeners.remove(listener);
    }

    public interface OnColorChangeListener {
        void onColorChanged(@ColorInt int color);
    }

}
