package com.miaxis.gunmanage.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.miaxis.gunmanage.app.Gun_App;
import com.miaxis.gunmanage.bean.Escort;
import com.device.Device;
import com.miaxis.gunmanage.event.LoginEvent;
import com.miaxis.gunmanage.event.PlayVoiceEvent;
import com.miaxis.gunmanage.event.ToastEvent;
import com.miaxis.gunmanage.greendao.gen.EscortDao;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LoginVerifyService extends IntentService {
    private static final String ACTION_LOGIN_VERIFY = "com.miaxis.gunmanage.service.action.LOGIN_VERIFY";


    public LoginVerifyService() {
        super("LoginVerifyService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context) {
        Intent intent = new Intent(context, LoginVerifyService.class);
        intent.setAction(ACTION_LOGIN_VERIFY);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOGIN_VERIFY.equals(action)) {
                handleActionLoginVerify();
            }
        }
    }

    private void handleActionLoginVerify() {
        try {
            EscortDao escortDao = Gun_App.getEscortDao();
            long countEscort = escortDao.count();
            if (countEscort <= 0) {
                EventBus.getDefault().post(new ToastEvent("登录失败: 该机构没有押运员"));
                EventBus.getDefault().post(new LoginEvent(null));
                return;
            }
            Device.openFinger();
            Thread.sleep(500);
            byte[] finger = new byte[200];
            byte[] message = new byte[100];
            EventBus.getDefault().post(new PlayVoiceEvent("请按手指"));
            int result = Device.getFinger(10000, finger, message);
            if (result != 0) {
                String errmsg = new String(message, "GBK");
                EventBus.getDefault().post(new ToastEvent("登录失败:"+errmsg));
                return;
            }
            List<Escort> escortList = escortDao.loadAll();
            for (int i=0; i<escortList.size(); i++) {
                for (int j=0; j<10; j++) {
                    String mFinger = escortList.get(i).getFinger(j);
                    if (mFinger == null || mFinger.length() == 0) {
                        continue;
                    }
                    result = Device.verifyFinger(mFinger, new String(finger).trim(), 3);
                    if (result == 0) {
                        EventBus.getDefault().post(new LoginEvent(escortList.get(i)));
                        return;
                    }
                }
            }
            EventBus.getDefault().post(new LoginEvent(null));
        } catch (Exception e) {
            EventBus.getDefault().post(new ToastEvent("登录失败:"+ e.getMessage()));
            EventBus.getDefault().post(new LoginEvent(null));
        } finally {
            Device.closeRfid();
            Device.closeFinger();
        }
    }

}
