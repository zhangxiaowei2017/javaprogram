package com.greer.Actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinGroup;
import akka.routing.RoundRobinPool;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ActorGroup {
    public final static ActorSystem actorSystem = ActorSystem.create("akkaSystem",ConfigFactory.load().getConfig("akka.actor"));
    public final static Map<String , ActorRef> actorRefMap = new HashMap<>(3);
    static {
//        System.out.println(ConfigFactory.load().getConfig("akka.actor").getConfig("default-dispatcher"));
        //注册每一个actor
        final ActorRef testActor = actorSystem.actorOf(Props.create(TestActor.class)) ;
        final ActorRef printActor = actorSystem.actorOf(Props.create(PrintActor.class)) ;
        final ActorRef blockingActor = actorSystem.actorOf(Props.create(BlockingFutureActor.class)) ;
        final ActorRef routee01 = actorSystem.actorOf(Props.create(Routee01.class).withDispatcher("my-blocking-dispatcher"),"route-1");
        final ActorRef routee02 = actorSystem.actorOf(Props.create(Routee02.class),"route-2");
        final ActorRef routee03 = actorSystem.actorOf(Props.create(Routee03.class),"route-3");


        //注册一个路由器对象
//        ActorRef workerRouter = actorSystem.actorOf(Props.create(GetStringActor.class).withRouter(new RoundRobinPool(2)),"router") ;
        //利用转发器注册一个路由器对象
        List<ActorRef> routees = new ArrayList<>(3) ;
        routees.add(routee01) ;
        routees.add(routee02) ;
        routees.add(routee03) ;

        Iterable<String> iterable1 = routees.stream().map(routee -> {
            return routee.path().toStringWithoutAddress();
        }).collect(Collectors.toList());

        ActorRef routerRef = actorSystem.actorOf(new RoundRobinGroup(iterable1).props()) ;
//        List<ActorRef> list = Arrays.asList(1,2).stream().map(x -> actorSystem.actorOf(Props.create(GetStringActor.class).withDispatcher("blocking-io-dispatcher"))).collect(Collectors.toList());
//        Iterable<String> routeeAddress = list.stream().map(x -> x.path().toStringWithoutAddress()).collect(Collectors.toList());
//
//        ActorRef getStringActor = actorSystem.actorOf(Props.create(GetStringActor.class).withDispatcher("my-blocking-dispatcher"),"getStringActor") ;
//        List<ActorRef> actorRefList = new ArrayList<>() ;
//        ActorRef quickActor = actorSystem.actorOf(Props.create(QuickRespActor.class).withDispatcher("default-dispatcher"),"quickRespActor") ;
//        actorRefList.add(getStringActor) ;
//        actorRefList.add(quickActor) ;

//        ActorRef workerRouter = actorSystem.actorOf(new RoundRobinGroup(actorRefList.stream().map(actor -> actor.path().toStringWithoutAddress()).collect(Collectors.toList())).props());


        actorRefMap.put("workerRouter" , routerRef) ;
        actorRefMap.put("testActor", testActor) ;
        actorRefMap.put("printActor",printActor) ;
        actorRefMap.put("blockingActor",blockingActor) ;
//


    }
}
