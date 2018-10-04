package com.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.io.Tcp;
import akka.japi.pf.ReceiveBuilder;
import com.boot.FSMBoot;
import com.lightbend.akka.sample.GetRequest;
import com.msg.FlushMsg;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

public class ClientActor extends AbstractActor {
    Map<String , ActorRef> actorRefMap = FSMBoot.actorRefList;
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().matchEquals("sendMessage" , s -> {
            ActorRef actorRef  = actorRefMap.get("actorRef") ;
            /**
             * 第一步： actor当前为disconnected状态,此时向actor发送一个将其处于Connected状态的初始化信息
             */
            actorRef.tell(new Tcp.Connected(null,null), self());
            /**
             * 第二歩，actor处于connecte状态之后，向其发送GetRequest信息.
             * actor接收到该请求之后，将处于CONNECTED_AND_APPENDING状态
             */
            actorRef.tell(new GetRequest("key"),self());
            /**
             * 发送刷新消息队列
             */
            actorRef.tell(new FlushMsg(),self());
//            actorRef.tell(new Integer(12),self());
//            actorRef.tell("你没见过我我这种消息",self());


            actorRef.tell(new GetRequest("key"),self());
            actorRef.tell(new GetRequest("key"),self());
            actorRef.tell(new GetRequest("key"),self());
            actorRef.tell(new GetRequest("key"),self());
            actorRef.tell(new GetRequest("key"),self());
            actorRef.tell(new GetRequest("key"),self());
            actorRef.tell(new GetRequest("key"),self());
            actorRef.tell(new GetRequest("key"),self());
        }).match(Tcp.Connected.class,connected -> {
            System.out.println("demoClient是可用!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(new Date().toLocaleString());
        }).build();
    }
}
