package com.miaxis.gunmanage.view.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.miaxis.gunmanage.R;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity implements OnClickListener, OnInitListener {
    public static final String TAB_MYTASK = "mytask";
    public static final String TAB_MYSHIFT = "myshift";
    public static final String TAB_SYSTEM = "system";

    private TextView tvMyShiftTab;
    private TextView tv_MyTaskTab;
    private TextView tv_SystemTab;

    private Drawable dUpTaskPressed;
    private Drawable dUpTaskNormal;
    private Drawable dMyTaskPressed;
    private Drawable dMyTaskNormal;
    private Drawable dSystemPressed;
    private Drawable dSystemNormal;
    private TabHost tabHost;
    public WeakReference<TextToSpeech> ttsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ttsRef = new WeakReference<TextToSpeech>(new TextToSpeech(this, this));
        initView();

        initTabIntents();
        setMyShiftTab();
    }

    private void initView() {

        tvMyShiftTab = (TextView) findViewById(R.id.tab_myshift_text);
        tvMyShiftTab.setOnClickListener(this);
        tv_MyTaskTab = (TextView) findViewById(R.id.tab_mytask_text);
        tv_MyTaskTab.setOnClickListener(this);
        tv_SystemTab = (TextView) findViewById(R.id.tab_system_text);
        tv_SystemTab.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        hideSoftInput();
        switch(view.getId()){
            case R.id.tab_myshift_text:
                setMyShiftTab();
                break;
            case R.id.tab_mytask_text:
                setMyTaskTab();
                break;
            case R.id.tab_system_text:
                setSystemTab();
                break;
        }
    }

    private void setMyShiftTab(){
        if (tabHost != null) {
            setMyShiftText(true);
            tabHost.setCurrentTabByTag(TAB_MYSHIFT);
            setMyTaskText(false);
            setSystemText(false);
        }
    }
    private void setMyTaskTab(){
        if (tabHost != null) {
            setMyTaskText(true);
            tabHost.setCurrentTabByTag(TAB_MYTASK);
            setMyShiftText(false);
            setSystemText(false);
        }
    }
    private void setSystemTab(){
        if (tabHost != null) {
            setSystemText(true);
            tabHost.setCurrentTabByTag(TAB_SYSTEM);
            setMyShiftText(false);
            setMyTaskText(false);
        }
    }

    private void setMyShiftText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            tvMyShiftTab.setTextColor(getResources().getColor(R.color.tab_blue));
            if (dUpTaskPressed == null) {
                dUpTaskPressed = getResources().getDrawable(R.mipmap.tab_uptask_pressed);
            }
            drawable = dUpTaskPressed;
        } else {
            tvMyShiftTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (dUpTaskNormal == null) {
                dUpTaskNormal = getResources().getDrawable(R.mipmap.tab_uptask_normal);
            }
            drawable = dUpTaskNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tvMyShiftTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void setMyTaskText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            tv_MyTaskTab.setTextColor(getResources().getColor(R.color.tab_blue));
            if (dMyTaskPressed == null) {
                dMyTaskPressed = getResources().getDrawable(R.mipmap.tab_mytask_pressd);
            }
            drawable = dMyTaskPressed;
        } else {
            tv_MyTaskTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (dMyTaskNormal == null) {
                dMyTaskNormal = getResources().getDrawable(R.mipmap.tab_mytask_normal);
            }
            drawable = dMyTaskNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tv_MyTaskTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void setSystemText(boolean isSelected) {
        Drawable drawable = null;
        if (isSelected) {
            tv_SystemTab.setTextColor(getResources().getColor(R.color.tab_blue));
            if (dSystemPressed == null) {
                dSystemPressed = getResources().getDrawable(R.mipmap.tab_icon_setting_p);
            }
            drawable = dSystemPressed;
        } else {
            tv_SystemTab.setTextColor(getResources().getColor(R.color.tab_normal_color));
            if (dSystemNormal == null) {
                dSystemNormal = getResources().getDrawable(R.mipmap.tab_icon_setting_nl);
            }
            drawable = dSystemNormal;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            tv_SystemTab.setCompoundDrawables(null, drawable, null, null);
        }
    }

    private void initTabIntents() {
        tabHost =  getTabHost();
        TabSpec spec = tabHost.newTabSpec(TAB_MYSHIFT);
        spec.setIndicator(TAB_MYSHIFT);
        Intent upTaskIntent = new Intent(this,GunListActivity.class);
        spec.setContent(upTaskIntent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(TAB_MYTASK);
        spec.setIndicator(TAB_MYTASK);
        Intent myTaskIntent = new Intent(this, GunHandOverActivity.class);
        spec.setContent(myTaskIntent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec(TAB_SYSTEM);
        spec.setIndicator(TAB_SYSTEM);
        Intent systemIntent = new Intent(this, SystemActivity.class);
        spec.setContent(systemIntent);
        tabHost.addTab(spec);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dMyTaskNormal != null) {
            dMyTaskNormal.setCallback(null);
        }
        if (dMyTaskPressed != null) {
            dMyTaskPressed.setCallback(null);
        }
        if (dSystemNormal != null) {
            dSystemNormal.setCallback(null);
        }
        if (dSystemPressed != null) {
            dSystemPressed.setCallback(null);
        }

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = ttsRef.get().setLanguage(Locale.CHINESE);
            if (result==TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                showMessage("不支持中文语音");
            }
        }
    }

    protected void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void playVoice(String str){
        ttsRef.get().speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void hideSoftInput(){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

}
