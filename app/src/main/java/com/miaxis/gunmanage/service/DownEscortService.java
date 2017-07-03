package com.miaxis.gunmanage.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.miaxis.gunmanage.app.Gun_App;
import com.miaxis.gunmanage.bean.Config;
import com.miaxis.gunmanage.bean.Escort;
import com.miaxis.gunmanage.comm.BaseComm;
import com.miaxis.gunmanage.comm.DownEscortComm;
import com.miaxis.gunmanage.event.ConfigFinishEvent;
import com.miaxis.gunmanage.event.DownEscortEvent;
import com.miaxis.gunmanage.event.ToastEvent;
import com.miaxis.gunmanage.greendao.gen.EscortDao;

import org.greenrobot.eventbus.EventBus;

import java.net.Socket;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownEscortService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_DOWN_ESCORT = "com.miaxis.gunmanage.service.action.DOWN_ESCORT";

    // TODO: Rename parameters
    private static final String ORG_CODE = "com.miaxis.gunmanage.service.extra.ORG_CODE";

    public DownEscortService() {
        super("DownEscortService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionDownEscort(Context context, String orgCode) {
        Intent intent = new Intent(context, DownEscortService.class);
        intent.setAction(ACTION_DOWN_ESCORT);
        intent.putExtra(ORG_CODE, orgCode);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWN_ESCORT.equals(action)) {
                final String orgCode = intent.getStringExtra(ORG_CODE);
                handleActionDownEscort(orgCode);
            }
        }
    }

    private void handleActionDownEscort(String orgCode) {
        try {
            int re = doComm(orgCode);
            if (re == 0) {
                EventBus.getDefault().post(new DownEscortEvent());
            }
        } catch (Exception e) {
            EventBus.getDefault().post(new ToastEvent(e.getMessage()));
        }
    }

    private int doComm(String orgCode) throws Exception {
        Config config = Gun_App.config;
        EscortDao escortDao = Gun_App.getEscortDao();
        StringBuilder msgSb = new StringBuilder();
        Socket socket = BaseComm.connect(config.getIp(), config.getPort(), 10000, msgSb);
        if (socket == null) {
            return -1;
        }
        int result;
        DownEscortComm comm = new DownEscortComm(socket, orgCode);
        result = comm.executeComm();
        if (result == 0) {
            List<Escort> escorts = comm.getEscortList();
            if (escorts != null) {
                escortDao.deleteAll();
                escortDao.insertInTx(escorts);
            }
        }
        return result;
    }

}