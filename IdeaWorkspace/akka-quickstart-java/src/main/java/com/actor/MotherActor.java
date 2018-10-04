package com.actor;

import akka.actor.*;
import akka.io.Tcp;
import akka.japi.pf.ReceiveBuilder;
import com.msg.*;

import java.util.Optional;

/**
 * 妈妈的actor
 */
public class MotherActor extends AbstractActor {
    /**
     * 标志当前actor是否能够处理消息
     */
    private boolean canHandlerMessage = true;
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
    public void preStart() throws InterruptedException {
        /**
         * 准备开始的时候，发送连接正常的消息
         */
        Connected connected = new Connected();
        connected.setConnected("connected");
        self().tell(connected,self());
        Thread.sleep(15000);
        Disconnected disconnected = new Disconnected();
        disconnected.setDisconnecte("disconnected");
        self().tell(disconnected,self());
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
//        System.out.println("postStop.self(): " + self());
//        NoMoney noMoney = new NoMoney();
//        noMoney.setDes("妈妈生病了，不能 赚钱了，需要替换爸爸给你钱");
//        sender().tell(noMoney,self());
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
    public void preRestart(final Throwable reason,
                           final Optional<Object> message) throws Exception {

        /**
         * 准备重启的时候，此时actor无法处理消息
         * 向原来的actor发送一个disconnected消息，断开
         * 如果在actor断开阶段有向该actor发送的请求，那么akk会将
         * 该阶段请求的消息存放起来，当重启结束之后，再将存储起来的消息
         * 按照请求的顺序一一进行处理，既先请求先处理的策略。
         *
         */
//        Disconnected disconnected = new Disconnected();
//        disconnected.setDisconnecte("disconnected");
//        System.out.println("preRestart disconneted");
//        self().tell(disconnected,self());
        Thread.sleep(10000);
        System.out.println("-------------------------------");
        System.out.println("this.name : " + this.name + " ---this.age : " + this.age + "---this.id : " + this.id);
        System.out.println("preRestart : " + reason.getMessage());
        //输出准备重启时的actor对象
        System.out.println("preRestart.sender() : " + sender());
        System.out.println("preRestart.self() : " + self());
        super.preRestart(reason,message);
    }


    /**
     * 当MotherActor在线的时候，可以处理的消息
     * @return
     */
    private Receive onLine() {
        Receive receive = ReceiveBuilder.create().match(GetMoney.class, getMoney -> {
            System.out.println("在线的处理行为");
            ActorRef actorRef = sender();
//            int result = 9 / 0;
//            System.out.println("GetMoney :" + result);

            //向actorRef给出响应信息
            MoneyOfMother moneyOfMother = new MoneyOfMother();
            moneyOfMother.setMoneyCount(1212);
            actorRef.tell(moneyOfMother,self());
            //切换为离线状态
//            context().become(unLine().onMessage());
        }).match(Disconnected.class, disconnected -> {
            System.out.println("onLine ---> unLine");
            context().become(unLine().onMessage());
        }).build();

        return receive;
    }


    /**
     * 当Actor离线的时候
     * @return
     */
    private Receive unLine() {
        return ReceiveBuilder.create().match(GetMoney.class,getMoney -> {
            System.out.println("不在线的处理行为");
            System.out.println("我已经断掉了，不能再处理数据了，等一会吧");
            //再切换为默认的处理行为
//            context().unbecome();
        }).match(Connected.class,connected -> {
            System.out.println("unLine --> onLine");
            context().become(onLine().onMessage());
        }).build();

    }

    @Override
    public Receive createReceive() {
//        match(GetMoney.class, getMoney -> {
//            System.out.println("this.canHandlerMessage : " + canHandlerMessage);
//            if(canHandlerMessage) {
//                ActorRef actorRef = sender();
//                System.out.println("sonActor :" + actorRef);
//                System.out.println("getMoney.des : " + getMoney.getDes());
//                int result = 1 / 0;
//
//                //向actorRef给出响应信息
//                MoneyOfMother moneyOfMother = new MoneyOfMother();
//                moneyOfMother.setMoneyCount(1212);
//                actorRef.tell(moneyOfMother,self());
//            } else {
//                System.out.println("当前actor出现了异常，还没有恢复完成，请再等一等");
//            }
//
//        })
        /**
         * 当actor恢复之前，客户端发送的消息会保存队列中，当actor恢复的那一刻，会根据消息的类型，选择状态的处理行为。
         * 紧接着，在actor恢复之后，客户端发送的消息会根据已经分配好的状态的处理行为中对消息定义的类型与客户端的消息进行匹配，
         * 然后找到最终的处理动作。
         */
        Receive receive = ReceiveBuilder.create().match(Connected.class, connected -> {
            System.out.println("在线");
            context().become(onLine().onMessage());
        }).match(Disconnected.class , disconnected -> {
            System.out.println("离线");
            context().become(unLine().onMessage());
//            context().unbecome();
        }).match(GetMoney.class, getMoney ->  {
            System.out.println("默认状态");
        }).build();

        return receive;
    }
}
