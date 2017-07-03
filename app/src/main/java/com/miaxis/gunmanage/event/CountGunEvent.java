package com.miaxis.gunmanage.event;

/**
 * Created by xu.nan on 2017/7/3.
 */

public class CountGunEvent {
    private int gunCount;

    public CountGunEvent(int gunCount) {
        this.gunCount = gunCount;
    }

    public int getGunCount() {
        return gunCount;
    }

    public void setGunCount(int gunCount) {
        this.gunCount = gunCount;
    }
}
