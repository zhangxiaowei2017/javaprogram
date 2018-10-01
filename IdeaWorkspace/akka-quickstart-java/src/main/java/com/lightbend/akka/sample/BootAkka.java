package com.lightbend.akka.sample;
import akka.actor.ActorSystem;
import akka.actor.Props;
public class BootAkka {
    public static void main(String[] args) {
            System.out.println("server start.......");
            ActorSystem actorSystem = ActorSystem.create("akkademy");
            actorSystem.actorOf(Props.create(SetRequestActor.class),"remoteActor");

    }
}
