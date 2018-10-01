package com.lightbend.akka.sample;

import akka.actor.AbstractActor;

public class LocalActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        Receive receive = receiveBuilder().match(String.class, remoteResponse ->{
            System.out.println("remoteResponse ：" + remoteResponse);
        }).build();
        return receive;
    }
}
