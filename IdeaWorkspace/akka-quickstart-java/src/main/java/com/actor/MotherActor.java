package com.actor;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import com.msg.GetMoney;
import com.msg.MoneyOfMother;
import com.msg.NoMoney;
import scala.concurrent.duration.Duration;

import java.util.Optional;

/**
 * 妈妈的actor
 */
public class MotherActor extends AbstractActor {
    private String name ;
    private int age ;
    private String id ;
    public MotherActor(String name , int age , String id) {
        this.name = name;
        this.age = age ;
        this.id = id;
        System.out.println("有参构造方法");
    }
    public MotherActor() {
        System.out.println("MotherActor构造方法");
    }


    @Override
    public void preStart() {
        System.out.println("-------------------------------");
        System.out.println("this.name : " + this.name + " ---this.age : " + this.age + "---this.id : " + this.id);
        //输出准备start时的actor对象
        System.out.println("preStart.sender() : " + sender());
        System.out.println("preStart.self() : " + self());
        try {
            super.preStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        System.out.println("preStart");
    }

    @Override
    public void postStop() {
        System.out.println("-------------------------------");
        System.out.println("this.name : " + this.name + " ---this.age : " + this.age + "---this.id : " + this.id);
        System.out.println("postStop sender : " + sender());
        //输出停止之后的actor对象
        System.out.println("postStop.self(): " + self());
        NoMoney noMoney = new NoMoney();
        noMoney.setDes("妈妈生病了，不能 赚钱了，需要替换爸爸给你钱");
        sender().tell(noMoney,self());
        System.out.println("postStop");
//        try {
//            super.postStop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 在重启之后阶段，如果调用了super.postRestart方法,那么actor将会默认调用preStart方法，
     * preStart方法不是akka内部的默认方法（因为akka内部对该方法是一个空实现），而是在当前actor类
     * 中重写的preStart方法。
     * 在actor构造方法之后无法通过sender()方法访问到发送请求的actor对象
     * 但可以通过self()方法访问当前的发生异常的actor对象。
     * 在actor构造方法之前可以通过sender()方法和self()方法访问到发送请求的
     * actor对象和当前发生异常的actor对象。
     * @param reason
     * @throws Exception
     */
    @Override
    public void postRestart(final Throwable reason) throws Exception {
        System.out.println("-------------------------------");
        System.out.println("this.name : " + this.name + " ---this.age : " + this.age + "---this.id : " + this.id);
        //输出重启之后的actor对象
        System.out.println("postRestart.self() : " + self());
        System.out.println("postRestart.sender() : " + sender());
        System.out.println("postRestart" + reason.getMessage());
        super.postRestart(reason);  ///////
    }

    /**
     * 在准备重启阶段，如果调用了super.preRestart方法，那么actor将默认会在preRestart方法中
     * 调用postStop方法，此postStop方法不是akka内部默认的postStop方法（因为akka内部的该方法是空实现的方法）
     * 而是我们在当前actor对象中重写的postStop方法。
     * @param reason
     * @param message
     * @throws Exception
     */
    @Override
    public void preRestart(final Throwable reason, final Optional<Object> message) throws Exception {
        System.out.println("-------------------------------");
        System.out.println("this.name : " + this.name + " ---this.age : " + this.age + "---this.id : " + this.id);
        System.out.println("preRestart : " + reason.getMessage());
        //输出准备重启时的actor对象
        System.out.println("preRestart.sender() : " + sender());
        System.out.println("preRestart.self() : " + self());

        super.preRestart(reason,message);
    }

    @Override
    public Receive createReceive() {
        Receive receive = ReceiveBuilder.create().match(GetMoney.class, getMoney -> {
            ActorRef actorRef = sender();
            System.out.println("sonActor :" + actorRef);
            System.out.println("getMoney.des : " + getMoney.getDes());
            int result = 1 / 0;
            //向actorRef给出响应信息
            MoneyOfMother moneyOfMother = new MoneyOfMother();
            moneyOfMother.setMoneyCount(1212);
            actorRef.tell(moneyOfMother,self());
        }).matchEquals("timeout", s -> {
            System.out.println("我有可用了");
            System.out.println("timeout result : " + s);
        }).build();

        return receive;
    }
}
