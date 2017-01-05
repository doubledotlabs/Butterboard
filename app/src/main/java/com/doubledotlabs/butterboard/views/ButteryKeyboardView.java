package com.doubledotlabs.butterboard.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.ColorInt;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

import com.doubledotlabs.butterboard.R;
import com.doubledotlabs.butterboard.utils.ColorUtils;
import com.doubledotlabs.butterboard.utils.TextDrawable;

public class ButteryKeyboardView extends KeyboardView {

    private int color;
    private boolean isShifted;

    public ButteryKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButteryKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public ButteryKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setPreviewEnabled(false);
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
            if (getKeyboard().getShiftKeyIndex() >= 0 && getKeyboard().getShiftKeyIndex() < getKeyboard().getKeys().size())
                getKeyboard().getKeys().get(getKeyboard().getShiftKeyIndex()).icon = VectorDrawableCompat.create(getResources(), isShifted ? R.drawable.ic_capslock : R.drawable.ic_caps, getContext().getTheme());

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
                } else if (key.icon != null) {
                    if (key.icon instanceof TextDrawable) {
                        key.icon = new TextDrawable(getContext(), getShiftedLabel(((TextDrawable) key.icon).getText()), isDark ? Color.WHITE : Color.BLACK);
                    } else {
                        DrawableCompat.setTint(key.icon, isDark ? Color.WHITE : Color.BLACK);
                        if (key.icon instanceof AnimatedVectorDrawableCompat)
                            ((AnimatedVectorDrawableCompat) key.icon).start();
                    }
                }
            }
        }

        super.invalidateAllKeys();
    }

    private String getShiftedLabel(String label) {
        return isShifted ? label.toUpperCase() : label.toLowerCase();
    }
}
