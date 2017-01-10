package com.doubledotlabs.butterboard.services;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Vibrator;
import android.support.annotation.ColorInt;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import com.doubledotlabs.butterboard.Butterboard;
import com.doubledotlabs.butterboard.R;
import com.doubledotlabs.butterboard.views.ButteryKeyboardView;

/*TODO - CHANGE SIZE DUE TO SMALLER DPI DISPLAYS - 5 INCH AND BELOW*/
/*This is the entire service that is used by the entire layout_keyboard - this is the heart, lungs, and basically other parts of the
* layout_keyboard.*/
public class KeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener, Butterboard.OnColorChangeListener {

    private Butterboard butterboard;
    private Vibrator vibrator;

    private ButteryKeyboardView keyboardView;

    private long lastCapsTime;
    private boolean isCapsLock;

    @Override
    public void onCreate() {
        super.onCreate();
        butterboard = (Butterboard) getApplicationContext();
        butterboard.addListener(this);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    public void onDestroy() {
        butterboard.removeListener(this);
        super.onDestroy();
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection input = getCurrentInputConnection();
        playType(primaryCode);
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                input.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                long now = System.currentTimeMillis();

                if (isCapsLock) isCapsLock = false;
                else if (lastCapsTime + 800 > now)
                    isCapsLock = true;

                keyboardView.setShifted(isCapsLock || !keyboardView.isShifted());
                lastCapsTime = now;
                break;
            case Keyboard.KEYCODE_DONE:
                input.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && keyboardView.isShifted()) {
                    code = Character.toUpperCase(code);
                    keyboardView.setShifted(isCapsLock);
                }
                input.commitText(String.valueOf(code), 1);
        }

        vibrator.vibrate(20);
    }

    // TODO - FILL WITH GESTURES
    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {
    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (ButteryKeyboardView) getLayoutInflater().inflate(R.layout.layout_keyboard, null); // Avoid Passing Null - TODO: FIND ALTERNATIVE
        keyboardView.setKeyboard(new Keyboard(this, R.xml.qwerty));
        keyboardView.setOnKeyboardActionListener(this);

        onColorChanged(butterboard.getColor());
        return keyboardView;
    }

    //Sounds that are played according to key presses - Standard is used for all other keys
    private void playType(int keyCode) {
        AudioManager audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                audio.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                audio.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                audio.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                audio.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onColorChanged(@ColorInt int color) {
        if (keyboardView != null) keyboardView.setColor(color);
    }
}
