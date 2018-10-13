package com.greer.Actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.greer.bean.EscapeClassMsg;
import com.greer.bean.Home;
import scala.PartialFunction;

import java.util.Map;

/**
 * 该actor充当一个转发者的作用
 * 孩子actor给老师actor发送一条消息，告诉老师我要逃课，
 * 老师actor接收该消息，然后将该消息转发给了孩子actor的老妈actor,
 * 老妈最后向childActor响应了一条消息，你要是逃课我就揍你屁屁。
 */
public class TeacherActor extends AbstractActor {
    Map<String , ActorRef> actorRefMap = ActorGroup.actorRefMap;
    @Override
    public PartialFunction receive() {
        PartialFunction partialFunction = null;
        partialFunction = ReceiveBuilder.match(EscapeClassMsg.class,escapeClassMsg -> {
            //老师心想，我告诉你你妈你要逃课
            System.out.println("escapeClassMsg.getDes :　" + escapeClassMsg.getDes());
            System.out.println("teacherActor--->sender() : " + sender());
            System.out.println("TeacherActor.motherActor : " + actorRefMap.get("matherActor"));
            escapeClassMsg.setDes("你儿子想逃课");
            actorRefMap.get("matherActor").forward(escapeClassMsg,context());
        }).build();
        return partialFunction;
    }
}
