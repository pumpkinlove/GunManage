package com.miaxis.gunmanage.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.miaxis.gunmanage.R;
import com.miaxis.gunmanage.event.ConfigFinishEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfigActivity extends BaseActivity {

    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_middle)
    TextView tvMiddle;
    @BindView(R.id.tv_right)
    TextView tvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        tvMiddle.setText("设置");
    }

    @OnClick(R.id.tv_left)
    void onLeft() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConfigFinish(ConfigFinishEvent e) {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
