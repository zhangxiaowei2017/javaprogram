package com.lightbend.akka.sample;

import java.io.Serializable;

public class SetRequest implements Serializable {
    public final String key ;
    public final String value ;
    public SetRequest(String key ,String value) {
        this.key = key;
        this.value = value;
    }

}
