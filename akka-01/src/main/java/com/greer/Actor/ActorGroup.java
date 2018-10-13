package com.greer.Actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinGroup;
import akka.routing.RoundRobinPool;
import com.typesafe.config.ConfigFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ActorGroup {
    private final static ActorSystem actorSystem = ActorSystem.create("akkaSystem",ConfigFactory.load().getConfig("actor"));
    public final static Map<String , ActorRef> actorRefMap = new HashMap<>(3);
    static {
        //注册每一个actor
        final ActorRef fatherActor = actorSystem.actorOf(Props.create(FatherActor.class,() -> new FatherActor())) ;
        final ActorRef matherActor = actorSystem.actorOf(Props.create(MotherActor.class)) ;
        final ActorRef childActor = actorSystem.actorOf(Props.create(ChildActor.class)) ;
        final ActorRef teacherActor = actorSystem.actorOf(Props.create(TeacherActor.class)) ;
//        final ActorRef getStringActor = actorSystem.actorOf(Props.create(GetStringActor.class,() -> new GetStringActor())) ;
//        final ActorRef getStringActor1 = actorSystem.actorOf(Props.create(GetStringActor.class,() -> new GetStringActor()).withDispatcher("blocking-io-dispatcher")) ;
//        final ActorRef getStringActor1 = actorSystem.actorOf(Props.create(GetStringActor.class).withDispatcher("blocking-io-dispatcher")) ;
//        final ActorRef getStringActor2 = actorSystem.actorOf(Props.create(GetStringActor.class).withDispatcher("blocking-io-dispatcher")) ;
//        final ActorRef getStringActor3 = actorSystem.actorOf(Props.create(GetStringActor.class,() -> new GetStringActor()).withDispatcher("blocking-io-dispatcher")) ;

        //注册一个路由器对象
//        ActorRef workerRouter = actorSystem.actorOf(Props.create(GetStringActor.class).withRouter(new RoundRobinPool(2)),"router") ;
        //利用转发器注册一个路由器对象
//        List<ActorRef> routees = new ArrayList<>(3) ;
//        routees.add(getStringActor1) ;
//        routees.add(getStringActor2) ;
//        routees.add(getStringActor3) ;

//        Iterable<String> iterable = routees.stream().map(x -> {
//            return x.path().toStringWithoutAddress();
//        }).collect(Collectors.toList());

        List<ActorRef> list = Arrays.asList(1,2).stream().map(x -> actorSystem.actorOf(Props.create(GetStringActor.class).withDispatcher("blocking-io-dispatcher"))).collect(Collectors.toList());
        Iterable<String> routeeAddress = list.stream().map(x -> x.path().toStringWithoutAddress()).collect(Collectors.toList());

        ActorRef workerRouter = actorSystem.actorOf(new RoundRobinGroup(routeeAddress).props());

        actorRefMap.put("workerRouter" , workerRouter) ;
        actorRefMap.put("fatherActor",fatherActor);
        actorRefMap.put("matherActor",matherActor);
        actorRefMap.put("childActor",childActor);
        actorRefMap.put("teacherActor",teacherActor);
//        actorRefMap.put("getStringActor", getStringActor);


    }
}
