package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.japi.pf.ReceiveBuilder;
import com.greer.bean.EscapeClassMsg;
import com.greer.bean.FatherHaveMoney;
import com.greer.bean.FatherNoMoneyMsg;
import com.greer.bean.MotherHaveMoneyMsg;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.concurrent.TimeUnit;

public class ChildActor extends AbstractActor {

    @Override
    public PartialFunction receive() {
        PartialFunction partialFunction = null;
        partialFunction =  ReceiveBuilder.match(FatherHaveMoney.class, fatherHaveMoney ->  {
            System.out.println("child receive ");
            System.out.println("fatherHaveMoney des : " + fatherHaveMoney.getDes());
            //当接收的消息的类型为FatherHaveMoney，关掉当前的actor
            System.out.println("childActor----------> : " + self());
            //actor上下文会在4秒之后向fatherActor发送一条超时信息。
//            context().system().scheduler().scheduleOnce(Duration.create(0,TimeUnit.SECONDS),sender(),"timeout",context().system().dispatcher(),ActorRef.noSender());
            context().stop(self());
        }).match(FatherNoMoneyMsg.class, fatherNoMoneyMsg -> {
            System.out.println("child receive ");
            System.out.println("FatherNoMoneyMsg des : " + fatherNoMoneyMsg.getDes());
            //当接收的消息的类型为FatherNoMoneyMsg，关掉当前的actor
            System.out.println("childActor----------> : " + self());
            //actor上下文会在4秒之后向fatherActor发送一条超时信息。
            context().system().scheduler().scheduleOnce(Duration.create(8,TimeUnit.SECONDS),sender(),"timeout",context().system().dispatcher(),ActorRef.noSender());
            context().stop(self());
        }).match(MotherHaveMoneyMsg.class, motherHaveMoneyMsg -> {
            System.out.println("child receive ");
            System.out.println("motherHaveMoneyMsg des : " + motherHaveMoneyMsg.getDes());
            //当接收的消息的类型为MotherHaveMoneyMsg，关掉当前的actor
            System.out.println("childActor----------> : " + self());
            context().stop(self());
        }).match(FatherNoMoneyMsg.class, fatherNoMoneyMsg -> {
            System.out.println("child receive ");
            System.out.println("FatherNoMoneyMsg des : " + fatherNoMoneyMsg.getDes());
            //当接收的消息的类型为FatherNoMoneyMsg，关掉当前的actor
            System.out.println("childActor----------> : " + self());
            context().stop(self());
        }).match(String.class , checkMsg-> {
            System.out.println("checkMsg : " + checkMsg);
        }).match(EscapeClassMsg.class,escapeClassMsg -> {
            //motherActor最后发话了，不让逃课。
            System.out.println("escapeClassMsg.getDes :　" + escapeClassMsg.getDes());
            escapeClassMsg.setDes("我不敢逃了");
            sender().tell(escapeClassMsg,self());
        }).build() ;
        return partialFunction;
    }
}
