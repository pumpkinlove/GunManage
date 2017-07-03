package com.miaxis.gunmanage.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.gunmanage.R;
import com.miaxis.gunmanage.app.Gun_App;
import com.device.Device;
import com.miaxis.gunmanage.event.ConfigFinishEvent;
import com.miaxis.gunmanage.event.LoginEvent;
import com.miaxis.gunmanage.event.PlayVoiceEvent;
import com.miaxis.gunmanage.event.ToastEvent;
import com.miaxis.gunmanage.service.LoginVerifyService;
import com.miaxis.gunmanage.view.fragment.ConfigFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.fl_config)
    FrameLayout flConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initConfig();
    }

    private void initConfig() {
        if (Gun_App.config == null) {
            flConfig.setVisibility(View.VISIBLE);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            ConfigFragment fragment = new ConfigFragment();
            transaction.replace(R.id.fl_config, fragment);
            transaction.commit();
            btnLogin.setVisibility(View.INVISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConfigFinishEvent(ConfigFinishEvent e) {
        Gun_App.config = e.getConfig();
        flConfig.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent e) {
        btnLogin.setEnabled(true);
        if (null == e.getEscort()) {
            playVoiceMessage("指纹验证失败");
            showToast("登录失败");
        } else {
            Gun_App.curEscort = e.getEscort();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @OnClick(R.id.btn_login)
    void onLogin() {
        startActivity(new Intent(this, MainActivity.class));
//        btnLogin.setEnabled(false);
//        LoginVerifyService.startActionFoo(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void playVoiceMessage(String content) {
        EventBus.getDefault().post(new PlayVoiceEvent(content));
    }

    private void showToast(String content) {
        EventBus.getDefault().post(new ToastEvent(content));
    }
}
