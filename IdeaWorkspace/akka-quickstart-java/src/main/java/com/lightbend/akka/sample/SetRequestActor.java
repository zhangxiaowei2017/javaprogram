package com.lightbend.akka.sample;

import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.japi.pf.FI;

import java.util.HashMap;
import java.util.Map;

public class SetRequestActor extends AbstractActor {
    public final Map<String , Object> map = new HashMap<String, Object>();
    @Override
    public Receive createReceive() {
        Receive receive = receiveBuilder().match(SetRequest.class, setRequest -> {
            map.put(setRequest.key,setRequest.value);
            ActorRef actorRef = sender();
            System.out.println("actorRef : " + actorRef);
            //向发送请求的actor发送响应信息,
            actorRef.tell("save success",ActorRef.noSender());
        }).match(String.class, str ->{
            System.out.println("receive string : " + str);
            sender().tell("receive success", Actor.noSender());
        }).match(GetRequest.class,getRequest -> {
            ActorRef actorRef = sender();
            Object value = map.get(getRequest.getKey());
            if(value == null) {
                actorRef.tell(new KeyNotFoundMsg(getRequest.getKey(),new Status.Failure(new Exception("the key mapping value not exists"))),self());
            } else {
                actorRef.tell(value, ActorRef.noSender());
            }
        }).matchAny(request -> {
            ActorRef actorRef = sender();
            actorRef.tell(new Status.Failure(new Exception("不知道是什么类型的信息")),ActorRef.noSender());
        }).build();
        return receive;
    }
}
