package com.actor;

import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import com.msg.Disconnected;
import com.msg.GetMoney;
import com.msg.MoneyOfMother;
import com.msg.NoMoney;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import static com.boot.BootClient.actorSystem;


/**
 * 儿子的actor
 */
public class SonActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create().match(MoneyOfMother.class, moneyOfMother -> {
            ActorRef actorRef = sender();
            System.out.println("motherActor : " + actorRef);
            System.out.println("moneyOfMother.getMoney : " + moneyOfMother.getMoneyCount());
        }).matchEquals("sendMessage" , s -> {
            System.out.println("sonActor 开始发送消息了");
            ActorSelection motherActorRef = actorSystem.actorSelection("akka.tcp://actorSystem@192.168.207.2:2552/user/motherActor");
//            ActorRef motherActorRef  = actorSystem.actorOf(Props.create(MotherActor.class,self())) ;
            String motherActorRefString  = motherActorRef.anchorPath().toString() + "user/motherActor";
//            System.out.println("motherActorRef.achor : " + actorSystem.actorFor(motherActorRefString));
            GetMoney getMoney = new GetMoney();
            getMoney.setDes("我想要1000多块钱,来自儿子");
            System.out.println("motherActorRef : " + motherActorRef);
            motherActorRef.tell(getMoney,self());
            Thread.sleep(10000);
//            motherActorRef.tell(new Disconnected(),self());
            motherActorRef.tell(getMoney,self());
//            context().system().scheduler().scheduleOnce(Duration.create(5, TimeUnit.SECONDS),actorSystem.actorFor(motherActorRefString),getMoney,context().system().dispatcher(),ActorRef.noSender());
            System.out.println("第二次请求要钱");
            Thread.sleep(10000);
            motherActorRef.tell(getMoney,self());
            System.out.println("第三次请求");
        }).match(NoMoney.class,noMoney -> {
            System.out.println("孩子哇哇大哭：" + noMoney.getDes());

            //三秒之后再次发送消息
        }).matchAny(o -> {
            context().system().scheduler().scheduleOnce(Duration.create(3, TimeUnit.SECONDS),sender(),"timeout",context().system().dispatcher(),self());
        }).build();
        return receive;
    }
}
