package com.msg;

import java.io.Serializable;

/**
 * 断开的消息
 */
public class Disconnected implements Serializable {
    private String disconnecte ;

    public String getDisconnecte() {
        return disconnecte;
    }

    public void setDisconnecte(String disconnecte) {
        this.disconnecte = disconnecte;
    }
}
