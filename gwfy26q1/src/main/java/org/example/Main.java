package org.example;

import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;
import org.apache.pekko.actor.Props;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ActorSystem system = ActorSystem.create("performanceIssueSystem");

//        ActorRef slowActor = system.actorOf(Props.create(SlowActor.class), "slowActor");

        ActorRef myActor = system.actorOf(Props.create(MyActor.class), "MyActor");


        // Send multiple messages to the SlowActor
        for (int i = 0; i < 1000; i++) {
            myActor.tell("Message " + i, ActorRef.noSender());
        }

        // Allow some time for processing
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        system.terminate();
    }
}