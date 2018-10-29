package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class GetStringActor extends AbstractActor {

    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create().matchEquals("1", s -> {
            System.out.println("111111111111");
            System.out.println("sender:" + s + self() + "|" + context().system().dispatcher());
            Thread.sleep(1000);
            sender().tell(0, self());
        }).build();
    }
}
