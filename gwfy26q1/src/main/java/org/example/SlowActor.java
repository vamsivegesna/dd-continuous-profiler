package org.example;

import org.apache.pekko.actor.AbstractActor;

public class SlowActor extends AbstractActor {

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, this::onMessage)
                .build();
    }

    private void onMessage(String message) {
        try {
            // Simulated performance issue: inefficient sleep causing latency
            Thread.sleep(200);
            System.out.println("Processed message: " + message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
