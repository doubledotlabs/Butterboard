package com.doubledotlabs.butterboard.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import com.doubledotlabs.butterboard.utils.ColorUtils;
import com.doubledotlabs.butterboard.utils.TextDrawable;

public class ButteryKeyboardView extends KeyboardView {

    private int color;
    private boolean isShifted;

    public ButteryKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButteryKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public ButteryKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        setBackgroundColor(color);
        invalidateAllKeys();
    }

    public boolean isShifted() {
        return isShifted;
    }

    @Override
    public boolean setShifted(boolean isShifted) {
        this.isShifted = isShifted;
        if (getKeyboard() != null) {
            getKeyboard().setShifted(isShifted);
            invalidateAllKeys();
        }
        return super.setShifted(isShifted);
    }

    @Override
    public void invalidateAllKeys() {
        if (getKeyboard() != null) {
            boolean isDark = ColorUtils.isColorDark(color);
            for (Keyboard.Key key : getKeyboard().getKeys()) {
                if (key.label != null) {
                    key.icon = new TextDrawable(getContext(), getShiftedLabel(key.label.toString()), isDark ? Color.WHITE : Color.BLACK);
                    key.label = null;
                } else if (key.icon != null && key.icon instanceof TextDrawable) {
                    key.icon = new TextDrawable(getContext(), getShiftedLabel(((TextDrawable) key.icon).getText()), isDark ? Color.WHITE : Color.BLACK);
                } else if (key.icon != null) {
                    DrawableCompat.setTint(key.icon, isDark ? Color.WHITE : Color.BLACK);
                }
            }
        }

        super.invalidateAllKeys();
    }

    private String getShiftedLabel(String label) {
        return isShifted ? label.toUpperCase() : label.toLowerCase();
    }
}
