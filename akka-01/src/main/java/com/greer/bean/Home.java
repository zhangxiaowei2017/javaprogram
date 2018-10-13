package com.greer.bean;

import akka.actor.*;
import com.greer.Actor.*;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static akka.actor.TypedActor.context;
import static scala.compat.java8.FutureConverters.toJava;

public class Home {


    public static void main(String[] args) {
        Map<String , ActorRef> actorRefMap = ActorGroup.actorRefMap;
        //输出childActor的路径
        System.out.println("childActor sender : " + actorRefMap.get("childActor"));
        //输出fatherActor的路径
//        System.out.println("fatherActor : " + fatherActor);
        //输出matherActor的路径
        System.out.println("matherActor : " + actorRefMap.get("matherActor"));
        //孩子向父亲要钱
        GetMoneyMsg getMoneyMsg = new GetMoneyMsg();
        getMoneyMsg.setMoneyCount(100);
        //超时调度
        /**
         * 向matherActor发送消息，发送者为childActor
         */
//        matherActor.tell(getMoneyMsg,childActor);
        /**
         * 向fatherActor发送消息，发送者为childActor
         */
//        fatherActor.tell(getMoneyMsg,childActor);
//        actorContext.system().scheduler().scheduleOnce(Duration.create(0,TimeUnit.SECONDS),childActor,"timeout",context().system().dispatcher(),ActorRef.noSender());
//        context().actorOf(Props.create(FatherActor.class,() -> new FatherActor()));
        EscapeClassMsg escapeClassMsg = new EscapeClassMsg();
        escapeClassMsg.setDes("我想逃课");
        actorRefMap.get("teacherActor").tell(escapeClassMsg,actorRefMap.get("childActor"));
        ActorRef childRef = actorRefMap.get("childRef") ;
    }
}
