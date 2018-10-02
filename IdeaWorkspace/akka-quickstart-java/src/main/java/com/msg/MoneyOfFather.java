package com.msg;

import java.io.Serializable;

/**
 * 爸爸发送的消息对象
 */
public class MoneyOfFather  implements Serializable {
    /**
     * 钱数
     */
    private int moneyCount ;

    public int getMoneyCount() {
        return moneyCount;
    }

    public void setMoneyCount(int moneyCount) {
        this.moneyCount = moneyCount;
    }
}
