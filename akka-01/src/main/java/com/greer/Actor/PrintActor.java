package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

public class PrintActor extends AbstractActor {

    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create()
                .match(Integer.class, i -> {
                    System.out.println("PrintActor: " + i);
                })
                .build();
    }
}
