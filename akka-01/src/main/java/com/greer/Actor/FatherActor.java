package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import com.greer.bean.FatherNoMoneyMsg;
import com.greer.bean.GetMoneyMsg;
import scala.PartialFunction;

public class FatherActor extends AbstractActor {
    @Override
    public PartialFunction receive() {
        return ReceiveBuilder.match(GetMoneyMsg.class,msg -> {
            System.out.println("father receivce GetMoneyMsg ...............");
            System.out.println(msg.getMoneyCount());
            System.out.println("child--->sender " + sender());
            System.out.println("father self :" + self());
//            Thread.sleep(40000);
            FatherNoMoneyMsg fatherNoMoneyMsg = new FatherNoMoneyMsg();
            fatherNoMoneyMsg.setDes("爸爸没钱");
            sender().tell(fatherNoMoneyMsg,self());
        }).match(String.class,checkMsg -> {
            System.out.println("checkMsg :" + checkMsg);
        }).build();
    }
}
