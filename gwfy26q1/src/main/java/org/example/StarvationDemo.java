package org.example;

import org.apache.pekko.actor.ActorRef;
import org.apache.pekko.actor.ActorSystem;

public class StarvationDemo {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("StarvationSystem");
        ActorRef blockingActor = system.actorOf(BlockingActor.props(), "blockingActor");

        for (int i = 0; i < 1000; i++) {
            blockingActor.tell(i, ActorRef.noSender());
        }
    }
}
