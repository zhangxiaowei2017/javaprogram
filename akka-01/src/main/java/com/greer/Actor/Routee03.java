package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class Routee03 extends AbstractActor {
    @Override
    public PartialFunction receive() {
        System.out.println(context().system().dispatchers().lookup("default-dispatcher").configurator().config().entrySet());
        return ReceiveBuilder.create().matchEquals("3",s -> {
            System.out.println("self : " + self());
            System.out.println("3");
        }).build();
    }
}
