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
        setBackgroundColor(color);

        if (getKeyboard() != null) {
            boolean isDark = ColorUtils.isColorDark(color);
            for (Keyboard.Key key : getKeyboard().getKeys()) {
                if (key.label != null) {
                    key.icon = new TextDrawable(getContext(), key.label.toString(), isDark ? Color.WHITE : Color.BLACK);
                    key.label = null;
                } else if (key.icon != null && key.icon instanceof TextDrawable) {
                    key.icon = new TextDrawable(getContext(), ((TextDrawable) key.icon).getText(), isDark ? Color.WHITE : Color.BLACK);
                } else if (key.icon != null) {
                    DrawableCompat.setTint(key.icon, isDark ? Color.WHITE : Color.BLACK);
                }
            }

            invalidateAllKeys();
        }
    }
}
