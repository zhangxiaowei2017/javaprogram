package com.lightbend.akka.sample;

import java.io.Serializable;

public class GetRequest implements Serializable {
    private String key ;
    public GetRequest(String key) {
        this.key = key;
    }
    public String getKey(){
        return this.key ;
    }
}
