package com.miaxis.gunmanage.view.activity;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by xu.nan on 2017/6/29.
 */

public class BaseActivity extends Activity {

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus()!=null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
