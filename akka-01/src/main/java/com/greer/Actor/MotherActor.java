package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.dsl.Creators;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.Patterns.*;
import akka.pattern.PipeToSupport;
import com.greer.bean.EscapeClassMsg;
import com.greer.bean.GetMoneyMsg;
import com.greer.bean.MotherHaveMoneyMsg;
import scala.PartialFunction;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static akka.pattern.Patterns.ask;
import static akka.pattern.Patterns.pipe;
import static scala.compat.java8.FutureConverters.toJava;

public class MotherActor extends AbstractActor {
    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.match(GetMoneyMsg.class,getMoneyMsg ->  {
            System.out.println("mather receivce GetMoneyMsg ...............");
            System.out.println(getMoneyMsg.getMoneyCount());
            MotherHaveMoneyMsg motherHaveMoneyMsg = new MotherHaveMoneyMsg();
            motherHaveMoneyMsg.setDes("2000");
//            Thread.sleep(40000);
            System.out.println("mather self : " + self());
            sender().tell(motherHaveMoneyMsg,self());
        }).match(EscapeClassMsg.class,escapeClassMsg -> {
            System.out.println("mather self : " + self());
//            System.out.println("motherActor receive escapeClassMsg thank TeacherActor :" + sender());
            System.out.println("motherActor escapeClassMsg.getDes :" + escapeClassMsg.getDes());
            escapeClassMsg.setDes("你要是敢逃课，我打你小屁屁");
//            sender().tell(escapeClassMsg, self());
//            ActorRef childRef = sender();
            Future future = ask(sender(),escapeClassMsg,4000) ;
            pipe(future,context().system().dispatcher()).to(sender());
            ActorRef actorRef = sender();
            CompletionStage completionStage = toJava(future);
            CompletableFuture completedFuture = (CompletableFuture) completionStage;
            future.map(x -> {
                System.out.println("x : " + x);
                System.out.println("sender : " + sender());
                return sender();
            }, context().system().dispatcher()) ;

//            completionStage.handle((msg , t) -> {
//                System.out.println("futuer is success");
//                if(t == null) {
//                    System.out.println("msg.getDes :" + ((EscapeClassMsg)msg).getDes());
//                    System.out.println("child sender : " + sender());
//                } else {
//                    System.out.println("异常不为空");
//                }
//                return false;
//            });
        }).matchAny(s -> {
            System.out.println("unkown message");
        }).build();
    }
}
