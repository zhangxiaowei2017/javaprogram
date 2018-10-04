package com.msg;

import akka.actor.AbstractFSM;
import akka.actor.ActorRef;
import akka.io.Tcp;
import akka.japi.pf.FI;
import akka.japi.pf.ReceiveBuilder;
import com.boot.FSMBoot;
import com.lightbend.akka.sample.GetRequest;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import scala.Option;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DemoActor extends AbstractFSM<State,EventQueue> {
    Map<String , ActorRef> map = FSMBoot.actorRefList;
    {
        startWith(com.msg.State.DISCONNETED, new EventQueue());
        when(com.msg.State.DISCONNETED,matchEvent(FlushMsg.class, (flushMsg, requests) -> {
            System.out.println("flushMsg : " + flushMsg);
            /**
             * stay和goto的区别：
             * 1 两者都是进行状态的切换，只是stay是将状态仍旧切换到当前的状态，等于状态没发生改变。
             * 2 而goTo一般用于将状态切换到另一个状态，调用goTo切换状态的时候，会产生事务。
             * 3 而调用stay仍旧停留在当前状态时，不会产生事务。
             * 4 同一个状态之间调用goTo时，不会触发事务。
                     */
            State fsm = stay();
            System.out.println("fsm.toString() : " + fsm.toString());
            return goTo(com.msg.State.DISCONNETED) ;
        }).event(GetRequest.class,(msg , container) -> {
            System.out.println("DISCONNETED------>GetRequest");
            container.add(msg);
            System.out.println("container : " + container);
            return stay();
        }).event(Tcp.Connected.class, (msg, container) -> {
                    System.out.println("DISCyhhONNECTED------>Tcp.Connected");
            EventQueue eventQueue = (EventQueue)container;
                    System.out.println("eventQueue : " + eventQueue);
                    System.out.println("sender : " + sender());
//                    sender().tell("sendMessage" , self());
            if(eventQueue.isEmpty()) {
                System.out.println("goto.CONNECTED");
                return goTo(com.msg.State.CONNECTED);
            } else {
                System.out.println("goto.CONNECTED_AND_PENDING");
                return goTo(com.msg.State.CONNECTED_AND_PENDING) ;
            }
        })
        );
        when(com.msg.State.CONNECTED,matchEvent(FlushMsg.class,(flushMsg, requests) -> stay()).event(GetRequest.class,(msg , container) -> {
            System.out.println("CONNECTED----GetRequest.class");
            container.add(msg) ;
            System.out.println("State.CONNECTED.containenr : " + container);
            return goTo(com.msg.State.CONNECTED_AND_PENDING) ;
        }));

        when(com.msg.State.CONNECTED_AND_PENDING, matchEvent(FlushMsg.class,(msg , container) ->{
            /**
             * 如果actor在线，而且消息队列中存在消息，当接收到一个FlushMsg刷新消息的消息类型时，
             * 重新定义当前的消息队列，然后将消息队列中的消息逐一处理完。
             */
            System.out.println("CONNECTE_AND_PENDING---->FlushMsg");
            System.out.println("container : " + container);
            container = new EventQueue();
            //更新当前状态的数据，以供下一个状态使用。(生成了一个包含新数据的状态对象)
            State state = stay().using(container);
            //制造一个异常
//            int result = 1/0;

            return state;
        }).event(GetRequest.class,(msg , container) -> {
            /**
             *如果actor在线，且一直没有接收到FlushMsg消息类型的消息时，那么将消息添加到消息队列中，
             *将状态切换到CONNECTE_AND_PENDING状态。
             */
            System.out.println("CONNECTE_AND_PENDING----->GetRequest.class");
            EventQueue eventQueue = (EventQueue) container;
            System.out.println("container : " + container);
            eventQueue.add(msg) ;
            System.out.println("container : " + container);
            return goTo(com.msg.State.CONNECTED).using(new EventQueue());   //更新了状态数据，当到了下一个CONNECTE_AND_PENDINGT状态时，状态数据变为了new EventQueue()
        }));
        whenUnhandled(
                /**
                 *  matchEvent第一个参数是事件的类型，第二个参数是当前状态数据类型，
                 * 第三个是一个函数式接口，传递事件类型参数，状态数据类型参数，返回一个Sate对象。
                 */
                matchEvent(Integer.class,EventQueue.class,(i,s) -> {
                    System.out.println("这个消息我得干点事情，因为是一个整型事件类型");
                    System.out.println("输出这个事件数据: " + i);
                    System.out.println("输出状态数据 :" + s);
                    //之后将当前的状态切换到CONNECTED状态
                    return goTo(com.msg.State.CONNECTED);
                }).anyEvent((event,data) -> {  //第二个参数是一个当前状态数据，也就是一个集合对象。里面包含了当前状态的所有消息对象
                    System.out.println("这个消息我啥也不干");
                    System.out.println("data : " + data);
                    return goTo(com.msg.State.CONNECTED_AND_PENDING);
                })
        );

        onTransition(matchState(com.msg.State.DISCONNETED, com.msg.State.CONNECTED_AND_PENDING, () -> {
            System.out.println("change state date : " + (new java.util.Date().toLocaleString()));
            System.out.println("状态从DISOCONNECTED 转移到了CONNECTE_AND_PENDING");
        }).state(com.msg.State.DISCONNETED, com.msg.State.CONNECTED ,(s,s1) -> {
            System.out.println("change state date : " + (new java.util.Date().toLocaleString()));
            System.out.println("from : " + s);
            System.out.println("to : " + s1);
        }).state(com.msg.State.CONNECTED, com.msg.State.CONNECTED_AND_PENDING , () -> {
            System.out.println("change state date : " + (new java.util.Date().toLocaleString()));
            System.out.println("from connected to connected_and_pending");
        }).state(com.msg.State.CONNECTED_AND_PENDING , com.msg.State.CONNECTED, () -> {
            System.out.println("change state date : " + (new java.util.Date().toLocaleString()));
            System.out.println("from CONNECTED_AND_PENDING to CONNECTED");
        }));


        initialize();
    }
    public DemoActor() {
        System.out.println("执行DemoActor构造方法");
    }
    @Override
    public void preStart() throws Exception {
        //当actor开始的时候，定期向clientActor发送心跳，让clientActor知道DemoActor是可用的。
        ActorRef actorRef = map.get("clientActorRef") ;
        System.out.println("actorRef : " + actorRef);
        context().system().scheduler().schedule(Duration.ofSeconds(10),Duration.ofSeconds(20),actorRef,new Tcp.Connected(null,null),context().system().dispatcher(),self()) ;
        System.out.println("preStart()");
        super.preStart();
    }
    @Override
    public void postStop() {
        System.out.println("postStop()");
        super.postStop();
    }
    @Override
    public void preRestart(final Throwable reason, final Option<Object> message) throws Exception {
        System.out.println("preRestart.sender() : " + sender());
        System.out.println("preRestart.self() : " + self());
        sender().tell("哎啊，我出故障了", self());
        System.out.println("preRestart");
        super.preRestart(reason,message);
    }
    @Override
    public void postRestart(final Throwable reason) throws Exception {
        super.postRestart(reason);
        System.out.println("postRestart()");
    }
}
