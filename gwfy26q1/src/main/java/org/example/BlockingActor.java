package org.example;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.Props;

public class BlockingActor extends AbstractActor {

    public static Props props() {
        return Props.create(BlockingActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class, i -> {
                    try {
                        Thread.sleep(5000); // Simulates a blocking operation
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("Processed: " + i);
                })
                .build();
    }
}
