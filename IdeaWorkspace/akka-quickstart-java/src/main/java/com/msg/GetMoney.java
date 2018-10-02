package com.msg;

import akka.actor.ExtendedActorSystem;
import akka.remote.MessageSerializer;
import akka.remote.serialization.MessageContainerSerializer;
import com.sun.xml.internal.ws.developer.Serialization;
import sun.plugin2.message.Serializer;

import java.io.Serializable;

/**
 * 要钱的消息对象
 */
@Serialization
public class GetMoney implements Serializable {
    /**
     * 消息的描述
     */
    private String des ;



    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
