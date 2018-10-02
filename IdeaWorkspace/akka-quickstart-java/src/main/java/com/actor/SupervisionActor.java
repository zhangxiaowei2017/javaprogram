package com.actor;

import akka.actor.*;
import akka.japi.Function;
import scala.concurrent.duration.Duration;


import static akka.actor.SupervisorStrategy.*;

public class SupervisionActor extends UntypedActor{

    /**
     * 定义Actor监控策略
     */
    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create("1 minute"), new Function<Throwable, Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) {
                    if (t instanceof ArithmeticException) {
                        System.out.println("actor resume");
                        return resume();
                    } else if (t instanceof NullPointerException) {
                        return restart();
                    } else if (t instanceof IllegalArgumentException) {
                        return stop();
                    } else {
                        return escalate();
                    }
                }
            });

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Props) {
            // 返回一个子Actor，有父Actor负责监控
            getSender().tell(getContext().actorOf((Props) message), getSelf());
        } else {
            unhandled(message);
        }
    }

    /**
     * 自定义监控Actor的策略
     * User overridable definition the strategy to use for supervising
     * child actors.
     */
    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
