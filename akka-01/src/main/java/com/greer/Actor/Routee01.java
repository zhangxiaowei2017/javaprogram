package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class Routee01 extends AbstractActor {
    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create().matchEquals("1",s -> {
            System.out.println("self : " + self());
            System.out.println("1");
        }).build();
    }
}
