package com.boot;

import akka.actor.*;
import com.actor.MotherActor;
import com.actor.SonActor;
import com.msg.GetMoney;

import static akka.actor.TypedActor.self;

public class BootClient {
    public static ActorSystem actorSystem = ActorSystem.create("clientActorSystem");
    public static void main(String[] args) {
        ActorRef sonActorRef = actorSystem.actorOf(Props.create(SonActor.class),"sonActor") ;
        sonActorRef.tell("sendMessage", sonActorRef);
    }
}
