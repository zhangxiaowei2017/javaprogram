package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class Routee02 extends AbstractActor {
    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create().matchEquals("2",s -> {
            System.out.println("self : " + self());
            System.out.println("2");
        }).build();
    }
}
