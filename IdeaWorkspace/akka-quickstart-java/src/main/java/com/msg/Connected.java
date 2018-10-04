package com.msg;

import java.io.Serializable;

/**
 * connected类型的消息
 */
public class Connected implements Serializable {
    private String connected ;

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }
}
