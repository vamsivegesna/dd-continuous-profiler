package org.example;

import org.apache.pekko.actor.AbstractActor;

public class MyActor extends AbstractActor {

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, msg -> {
                    Thread.sleep(5000); // Blocks the default dispatcher
                    getSender().tell("Processed: " + msg, getSelf());
                })
                .build();
    }
}
