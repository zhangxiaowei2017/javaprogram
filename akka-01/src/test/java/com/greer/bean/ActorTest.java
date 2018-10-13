package com.greer.bean;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.greer.Actor.ChildActor;
import com.greer.Actor.FatherActor;
import com.greer.Actor.MotherActor;
import org.junit.Test;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import static org.junit.Assert.*;
import static scala.compat.java8.FutureConverters.toJava;

public class ActorTest {
    ActorSystem actorSystem = ActorSystem.create();
    @Test
    public void actorTest() {
        ActorRef fatherActor = actorSystem.actorOf(Props.create(FatherActor.class)) ;
        ActorRef matherActor = actorSystem.actorOf(Props.create(MotherActor.class)) ;
//        TestActorRef matherActor = TestActorRef.create(actorSystem,Props.create(MotherActor.class)) ;
//        TestActorRef childActor = TestActorRef.create(actorSystem,Props.create(ChildActor.class)) ;
        ActorRef childActor = actorSystem.actorOf(Props.create(ChildActor.class)) ;

        //孩子向父亲要钱
        GetMoneyMsg getMoneyMsg = new GetMoneyMsg();
        getMoneyMsg.setMoneyCount(100);
//        childActor.tell(getMoneyMsg,fatherActor);
        matherActor.tell(getMoneyMsg,childActor);
//        CompletionStage<Object> future = toJava(Patterns.ask(matherActor,getMoneyMsg,5000));
//        future.thenAccept(response -> {
//            System.out.println("response : " + (((MotherHaveMoneyMsg)response)).getDes());
//
//        });
    }
}