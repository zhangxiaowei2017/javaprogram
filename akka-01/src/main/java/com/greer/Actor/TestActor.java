package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

import java.util.Map;

public class TestActor extends AbstractActor {
    private Map<String , ActorRef> actorRefMap = ActorGroup.actorRefMap;
    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.create().matchEquals("start" , s -> {
            ActorRef workerRouter = actorRefMap.get("workerRouter") ;
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("1", self());
            workerRouter.tell("2", self());
            workerRouter.tell("2", self());
            workerRouter.tell("2", self());
            workerRouter.tell("2", self());
            workerRouter.tell("2", self());
            workerRouter.tell("2", self());
            workerRouter.tell("2", self());
            workerRouter.tell("2", self());
        }).match(Integer.class, s -> {
            System.out.println("getStringActor response integer is: " + s);
        }).build();
    }
}
