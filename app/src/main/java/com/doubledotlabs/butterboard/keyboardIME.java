package com.doubledotlabs.butterboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
/*TODO - CHANGE SIZE DUE TO SMALLER DPI DISPLAYS - 5 INCH AND BELOW*/
/*This is the entire service that is used by the entire keyboard - this is the heart, lungs, and basically other parts of the
* keyboard.*/
public class keyboardIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView keys;
    private Keyboard qwerty;
    private boolean caps = false;

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection input = getCurrentInputConnection();
        playType(primaryCode);
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE:
                input.deleteSurroundingText(1,0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                qwerty.setShifted(caps);
                keys.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                input.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code =(char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                input.commitText(String.valueOf(code),1);
        }
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
    public android.view.View onCreateInputView(){
        keys=(KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null); // Avoid Passing Null - TODO: FIND ALTERNATIVE
        qwerty = new Keyboard(this, R.xml.qwerty);
        keys.setKeyboard(qwerty);
        keys.setOnKeyboardActionListener(this);
        return keys;
    }
    //Sounds that are played according to key presses - Standard is used for all other keys
    private void playType (int keyCode){
        AudioManager audio = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode){
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
            default: audio.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }
}
