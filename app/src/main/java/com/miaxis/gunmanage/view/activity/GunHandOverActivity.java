package com.miaxis.gunmanage.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.miaxis.gunmanage.R;

import butterknife.OnClick;

import static com.miaxis.gunmanage.app.Gun_App.curEscort;

public class GunHandOverActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun_hand_over);
    }

    @OnClick(R.id.btn_borrow_gun)
    void onGiveGun() {
    }
}
