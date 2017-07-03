package com.miaxis.gunmanage.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.miaxis.gunmanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemActivity extends BaseActivity {

    @BindView(R.id.ll_config)
    LinearLayout llConfig;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.btn_exit)
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_config)
    void onConfig() {
        startActivity(new Intent(this, ConfigActivity.class));
    }

    @OnClick(R.id.ll_update)
    void onUpdate() {

    }

    @OnClick(R.id.ll_about)
    void onAbout() {

    }

    @OnClick(R.id.btn_exit)
    void onExit() {

    }

}