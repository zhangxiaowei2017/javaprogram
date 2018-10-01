package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.japi.pf.FI;
import scala.util.Failure;

/**
 * 该actor，根据发送的消息ping，返回一个响应pang字符串
 */
public class PongActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        Receive receive = receiveBuilder().matchEquals("ping", s -> {
            System.out.println("s ->>>>>>" + s);
            sender().tell("pong",self());
        }).matchAny(s -> {
            System.out.println("info is unkown");
            sender().tell(new Status.Failure(new Exception("info is error")),self());
        }).build();
        return receive;

    }
}