package com.miaxis.gunmanage.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;

import com.miaxis.gunmanage.bean.Config;
import com.miaxis.gunmanage.bean.Escort;
import com.miaxis.gunmanage.bean.TimeStamp;
import com.miaxis.gunmanage.event.PlayVoiceEvent;
import com.miaxis.gunmanage.event.ToastEvent;
import com.miaxis.gunmanage.greendao.gen.ConfigDao;
import com.miaxis.gunmanage.greendao.gen.DaoMaster;
import com.miaxis.gunmanage.greendao.gen.DaoSession;
import com.miaxis.gunmanage.greendao.gen.EscortDao;
import com.miaxis.gunmanage.greendao.gen.GunDao;
import com.miaxis.gunmanage.greendao.gen.TimeStampDao;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by xu.nan on 2017/6/29.
 */

public class Gun_App extends Application implements TextToSpeech.OnInitListener {

    public static ConfigDao configDao;
    public static EscortDao escortDao;
    public static GunDao gunDao;
    public static TimeStampDao timeStampDao;
    public static Config config;
    public static Escort curEscort;
    public WeakReference<TextToSpeech> ttsRef;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        initDbHelp();
        initConfig();
        initTTF();
    }

    private void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), "Gun_Manage.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        configDao = daoSession.getConfigDao();
        escortDao = daoSession.getEscortDao();
        gunDao = daoSession.getGunDao();
        timeStampDao = daoSession.getTimeStampDao();
    }

    public static void initConfig() {
        try {
            config = configDao.loadByRowId(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTTF() {
        ttsRef = new WeakReference<>(new TextToSpeech(this, this));
    }

    public static ConfigDao getConfigDao() {
        return configDao;
    }

    public static EscortDao getEscortDao() {
        return escortDao;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVoiceEvent(PlayVoiceEvent e) {
        ttsRef.get().speak(e.getContent(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = ttsRef.get().setLanguage(Locale.CHINESE);
            if (result==TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                EventBus.getDefault().post(new ToastEvent("不支持中文语音"));
            }
        }
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }
}
