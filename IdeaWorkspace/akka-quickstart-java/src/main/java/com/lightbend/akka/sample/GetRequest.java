package com.lightbend.akka.sample;

import com.msg.Request;

import java.io.Serializable;

public class GetRequest extends Request implements Serializable {
    private String key ;
    public GetRequest(String key) {
        this.key = key;
    }
    public String getKey(){
        return this.key ;
    }
}
