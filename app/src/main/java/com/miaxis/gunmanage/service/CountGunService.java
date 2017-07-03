package com.miaxis.gunmanage.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.text.TextUtils;

import com.miaxis.gunmanage.bean.Gun;
import com.miaxis.gunmanage.bean.TimeStamp;
import com.miaxis.gunmanage.comm.BaseComm;
import com.miaxis.gunmanage.comm.CountGunComm;
import com.miaxis.gunmanage.comm.DownGunComm;
import com.miaxis.gunmanage.event.CountGunEvent;
import com.miaxis.gunmanage.event.DownGunEvent;
import com.miaxis.gunmanage.event.ToastEvent;

import org.greenrobot.eventbus.EventBus;

import java.net.Socket;
import java.util.List;

import static com.miaxis.gunmanage.app.Gun_App.config;
import static com.miaxis.gunmanage.app.Gun_App.gunDao;
import static com.miaxis.gunmanage.app.Gun_App.timeStampDao;

public class CountGunService extends IntentService {
    private static final String ACTION_COUNT_GUN = "com.miaxis.gunmanage.service.action.COUNT_GUN";

    public CountGunService() {
        super("CountGunService");
    }

    public static void startActionCountGun(Context context) {
        Intent intent = new Intent(context, CountGunService.class);
        intent.setAction(ACTION_COUNT_GUN);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_COUNT_GUN.equals(action)) {
                handleActionDownGun();
            }
        }
    }

    private void handleActionDownGun() {
        int re = doComm();
        if (re != 0) {
            EventBus.getDefault().post(new ToastEvent("下载枪支列表失败"));
        }
    }

    private int doComm() {
        try {
            TimeStamp gunStamp = timeStampDao.loadByRowId(2);
            String timeStamp = "";
            if (gunStamp != null) {
                timeStamp = gunStamp.getOpDateTime();
                if (TextUtils.isEmpty(timeStamp)) {
                    timeStamp = "";
                }
            } else {
                timeStamp = "";
            }
            StringBuilder sbMsg = new StringBuilder();
            Socket socket = BaseComm.connect(config.getIp(), config.getPort(), 10000, sbMsg);
            if (socket == null) {
                return -1;
            }
            int result;
            CountGunComm comm = new CountGunComm(socket, config.getOrgCode(), timeStamp);
            result = comm.executeComm();
            if (result == 0) {
                EventBus.getDefault().post(new CountGunEvent(comm.getGunCount()));
            }
            return result;
        } catch (Exception e) {
            return -1;
        }
    }
}
