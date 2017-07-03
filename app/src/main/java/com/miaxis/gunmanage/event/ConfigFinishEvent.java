package com.miaxis.gunmanage.event;

import com.miaxis.gunmanage.bean.Config;

/**
 * Created by xu.nan on 2017/6/30.
 */

public class ConfigFinishEvent {
    private Config config;

    public ConfigFinishEvent(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
