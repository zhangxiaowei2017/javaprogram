package com.boot;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;
import com.actor.ClientActor;
import com.lightbend.akka.sample.GetRequest;
import com.msg.DemoActor;
import com.msg.FlushMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FSMBoot {
    private static Logger logger = LoggerFactory.getLogger(Object.class.toString()) ;
    public static Map<String , ActorRef> actorRefList = new HashMap<String , ActorRef>();
    public static ActorSystem actorSystem = ActorSystem.create("fsmSystem");

    public static void main(String[] args) {
//        ActorRef actorRef = actorSystem.actorOf(Props.create(DemoActor.class)) ;
//        ActorRef clientActorRef = actorSystem.actorOf(Props.create(ClientActor.class)) ;
//
//        actorRefList.put("actorRef",actorRef) ;
//        actorRefList.put("clientActorRef" , clientActorRef) ;
//        clientActorRef.tell("sendMessage",ActorRef.noSender());

        logger.debug("hello,world!!!!!!!!!!");
        logger.debug("hello,world!!!!!!!!!!");
        logger.debug("hello,world!!!!!!!!!!");
        logger.debug("{}", "okay");
    }
}
