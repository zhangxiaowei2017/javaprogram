package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class QuickRespActor extends AbstractActor {
    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create().matchEquals("2",s -> {
            System.out.println("2222222222222");
            System.out.println("sender:" + s + "|" + getContext().props().dispatcher());
            sender().tell(0,ActorRef.noSender());
        }).build();
    }
}
