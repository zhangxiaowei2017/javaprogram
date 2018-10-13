package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class GetStringActor extends AbstractActor {

    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create().matchEquals("1", s -> {
            System.out.println("sender:" + s + "|" + self() + context().system().dispatcher());
            sender().tell("sleep" , ActorRef.noSender());
            Thread.sleep(10000);
            System.out.println("sender : " + sender());
        }).matchEquals("2", s -> {
            System.out.println("sender:" + s + "|" + self());
            sender().tell("sleep" , ActorRef.noSender());
//            Thread.sleep(10000);
            System.out.println("sender : " + sender());
        }).build();
    }
}
