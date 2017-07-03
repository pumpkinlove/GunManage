package com.miaxis.gunmanage.event;

/**
 * Created by xu.nan on 2017/7/3.
 */

public class CountEscortEvent {
    private int escortCount;

    public CountEscortEvent(int escortCount) {
        this.escortCount = escortCount;
    }

    public int getEscortCount() {
        return escortCount;
    }

    public void setEscortCount(int escortCount) {
        this.escortCount = escortCount;
    }
}
