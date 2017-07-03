package com.miaxis.gunmanage.event;

import com.miaxis.gunmanage.bean.Escort;

/**
 * Created by xu.nan on 2017/6/30.
 */

public class LoginEvent {
    private Escort escort;

    public LoginEvent(Escort escort) {
        this.escort = escort;
    }

    public Escort getEscort() {
        return escort;
    }

    public void setEscort(Escort escort) {
        this.escort = escort;
    }
}
