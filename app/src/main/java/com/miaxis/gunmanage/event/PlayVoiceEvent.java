package com.miaxis.gunmanage.event;

/**
 * Created by xu.nan on 2017/6/30.
 */

public class PlayVoiceEvent {
    private String content;

    public PlayVoiceEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
