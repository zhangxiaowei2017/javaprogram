package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.dispatch.Futures;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

public class BlockingFutureActor extends AbstractActor {
    ExecutionContext ec = getContext().dispatcher();
//    ExecutionContext ec = getContext().system().dispatchers().lookup("my-blocking-dispatcher");

    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create()
                .match(Integer.class, i -> {
                    System.out.println("blocking call "+ i);
//                    System.out.println(ActorGroup.actorSystem.settings());
                    Future<Integer> f = Futures.future(() -> {
                        Thread.sleep(5000);
                        System.out.println("Blocking future finished: " + i);
                        return i;
                    },ec);
                })
                .build();
    }
}
