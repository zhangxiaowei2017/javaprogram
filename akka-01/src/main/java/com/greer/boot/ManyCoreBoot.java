package com.greer.boot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.greer.Actor.ActorGroup;
import com.jasongoodwin.monads.Futures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static akka.pattern.Patterns.ask;
import static scala.compat.java8.FutureConverters.toJava;

public class ManyCoreBoot {
    static Map<String,ActorRef> actorRefMap = ActorGroup.actorRefMap;
    /**
     * 在多个cpu多个核上使用actor执行多个任务
     */
    public static void useManyCoresOnFuture() {
        List<String> stringList = new ArrayList<>(5) ;
        stringList.add("1") ;
        stringList.add("2") ;
        stringList.add("3") ;
        stringList.add("4") ;
        stringList.add("5") ;

        //使用流执行并发请求GetStringActor
        ActorRef actorRef = actorRefMap.get("getStringActor") ;
        System.out.println("actorRef : " + actorRef);
        Stream<CompletableFuture<Object>> completableFutureStream = stringList.stream()
                                        .map(s -> ((CompletableFuture)toJava(ask(actorRef,s,2000))));
        List<CompletableFuture<Object>> completableFutures = completableFutureStream.collect(Collectors.toList());

        Future<List<Object>> futureList = Futures.sequence(completableFutures) ;

        ((CompletableFuture<List<Object>>) futureList).thenAccept(list ->{
//            System.out.println("list : " + list);
//            list.stream().map(string -> {
//                System.out.println("response : " + string);
//                return string;
//            }) ;
            list.stream().forEach(s -> {
                System.out.println("----------------");
                System.out.println("response : " + s);
            });
        }) ;
        System.out.println("futureList : " + futureList);
    }

    /**
     * 使用actor实现多核编程
     */
    public static void manyCoresOnActor() {
        /**
         * 要完成使用actor多核编程需要使用router，通过路由器将消息路由的actor对象上。
         */
        ActorRef router = actorRefMap.get("workerRouter") ;
//        router.tell(new akka.routing.Broadcast("broadcast"),ActorRef.noSender());
        router.tell("1" , ActorRef.noSender());
        router.tell("1" , ActorRef.noSender());
        router.tell("2" , ActorRef.noSender());
//        router.tell("3" , ActorRef.noSender());
        System.out.println("路由器的路径为：" + router);
    }

    public static void main(String[] args) {
        manyCoresOnActor();
    }
}
