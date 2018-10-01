package com.lightbend.akka.sample;

import akka.actor.Status;

import java.io.Serializable;

/**
 * 当用户请求的信息找不到时，给出该类型的信息响应
 */
public class KeyNotFoundMsg implements Serializable {
    /**
     * 请求的信息的key
     */
    private final String key ;
    /**
     *
     */
    private final Status.Failure failure;
    public KeyNotFoundMsg(String key, Status.Failure failure) {
        this.key = key;
        this.failure = failure;
    }

    public String getKey () {
        return this.key;
    }
    public Status.Failure getFailure () {
        return this.failure;
    }
}
