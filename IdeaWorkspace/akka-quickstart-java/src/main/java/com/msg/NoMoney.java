package com.msg;

import java.io.Serializable;

/**
 * 没钱的信息类型
 */
public class NoMoney implements Serializable {
    private String des ;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
