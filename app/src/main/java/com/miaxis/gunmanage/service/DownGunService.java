package com.miaxis.gunmanage.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.text.TextUtils;

import com.miaxis.gunmanage.app.Gun_App;
import com.miaxis.gunmanage.bean.Gun;
import com.miaxis.gunmanage.bean.TimeStamp;
import com.miaxis.gunmanage.comm.BaseComm;
import com.miaxis.gunmanage.comm.DownEscortComm;
import com.miaxis.gunmanage.comm.DownGunComm;
import com.miaxis.gunmanage.event.DownGunEvent;
import com.miaxis.gunmanage.event.ToastEvent;
import com.miaxis.gunmanage.greendao.gen.GunDao;

import org.greenrobot.eventbus.EventBus;

import java.net.Socket;
import java.util.List;

import static com.miaxis.gunmanage.app.Gun_App.config;
import static com.miaxis.gunmanage.app.Gun_App.gunDao;
import static com.miaxis.gunmanage.app.Gun_App.timeStampDao;

public class DownGunService extends IntentService {
    private static final String ACTION_DOWN_GUN = "com.miaxis.gunmanage.service.action.DOWN_GUN";

    private static final String PAGE_NUM = "com.miaxis.gunmanage.service.extra.PAGE_NUM";
    private static final String PAGE_SIZE = "com.miaxis.gunmanage.service.extra.PAGE_SIZE";

    public DownGunService() {
        super("DownGunService");
    }

    public static void startActionDownGun(Context context, String pageNum, String pageSize) {
        Intent intent = new Intent(context, DownGunService.class);
        intent.setAction(ACTION_DOWN_GUN);
        intent.putExtra(PAGE_NUM, pageNum);
        intent.putExtra(PAGE_SIZE, pageSize);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWN_GUN.equals(action)) {
                final String pageNum = intent.getStringExtra(PAGE_NUM);
                final String pageSize = intent.getStringExtra(PAGE_SIZE);
                handleActionDownGun(pageNum, pageSize);
            }
        }
    }

    private void handleActionDownGun(String pageNum, String pageSize) {
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
                throw new Exception("连接失败" + sbMsg.toString());
            }
            int result;
            DownGunComm comm = new DownGunComm(socket, pageNum, config.getOrgCode(), timeStamp, pageSize);
            result = comm.executeComm();
            if (result == 0) {
                List<Gun> gunList = comm.getGunList();
                if (gunList != null && gunList.size() > 0) {
                    gunDao.insertOrReplaceInTx(gunList);
                    if (gunStamp == null) {
                        gunStamp = new TimeStamp();
                    }
                    gunStamp.setId(2L);
                    gunStamp.setStampName("GUN_STAMP");
                    gunStamp.setOpDateTime(comm.getTimeStamp());
                    timeStampDao.insertOrReplace(gunStamp);
                }
            }
        } catch (Exception e) {
            EventBus.getDefault().post(new ToastEvent("下载枪支列表异常" + e.getMessage()));
        } finally {
            EventBus.getDefault().post(new DownGunEvent());
        }
    }


}
