package com.boot;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.actor.MotherActor;
import com.actor.SonActor;
import com.msg.GetMoney;

public class BootServer {
    public static ActorSystem actorSystem = ActorSystem.create("actorSystem");
    public static void main(String[] args) {
        /**
         * 注册各个actor对象
         * 安全重启：
         * 我们可以通过actorOf方法注册actor对象
         * 其中actorOf的构造方法接受一个Props对象
         * Props.create(Actor对象类)
         *
         * 可以采用两种方式注册actor：
         * Props.create(MotherActor.class)，该方式会导致actor对象在重启的时候，
         * 初始化的状态信息会消失，比如数据库端口号信息，数据库连接信息，为了解决重启
         * 之后端口号和链接等任何状态信息可以再次被恢复使用，那么akka提供了一种基于安
         * 全重启的create方法，既第二种方式注册actor对象，Props.create(MotherAc
         * tor.class,...)该方法第二个参数可以传递多个信息变量，这些信息变量将会自动
         * 注入到actor对象的构造函数中，当actor重启之后，这些信息变量仍然会存在新的
         * actor对象中而不会丢失。方便actor对象下次继续使用。
         */
        ActorRef motherActorRef = actorSystem.actorOf(Props.create(MotherActor.class,"zhang",12,"1-101"),"motherActor");
        System.out.println("motherActorRef : " + motherActorRef);
    }
}
