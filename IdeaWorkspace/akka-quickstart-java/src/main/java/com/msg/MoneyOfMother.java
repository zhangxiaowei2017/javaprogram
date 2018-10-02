package com.msg;

import akka.actor.ExtendedActorSystem;
import akka.remote.serialization.MessageContainerSerializer;
import com.sun.xml.internal.ws.developer.Serialization;

import java.io.Serializable;

/**
 * 妈妈发送的消息对象类型
 */
public class MoneyOfMother implements Serializable{
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
