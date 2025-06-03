//package org.example;
//
//import com.typesafe.config.ConfigFactory;
//import org.apache.pekko.actor.AbstractActor;
//import org.apache.pekko.actor.ActorRef;
//import org.apache.pekko.actor.ActorSystem;
//import org.apache.pekko.actor.Props;
//import rx.Observable;
//import rx.observables.ConnectableObservable;
//
//import java.util.concurrent.TimeUnit;
//
//public class ThreadExhaustionDemo {
//
//    public static void main(String[] args) {
//        ActorSystem system = ActorSystem.create("ThreadExhaustionSystem", ConfigFactory.load());
//
//        ActorRef pubSubChannel = system.actorOf(Props.create(PubSubChannel.class).withDispatcher("default-dispatcher"));
//        ActorRef roomActor = system.actorOf(Props.create(RoomActor.class).withDispatcher("throughput-20-dispatcher"));
//
//        Observable<Integer> observable1 = Observable.interval(1000, TimeUnit.MILLISECONDS)
//                .take(5)
//                .map(Long::intValue);
//
//        Observable<Integer> observable2 = Observable.create(subscriber -> {
//            try {
//                Thread.sleep(10000); // Simulate a blocking operation
//                subscriber.onNext(1);
//                subscriber.onCompleted();
//            } catch (InterruptedException e) {
//                subscriber.onError(e);
//            }
//        });
//
//        ConnectableObservable<Integer> connectableObservable = Observable.merge(observable1, observable2).publish();
//
//        pubSubChannel.tell(new StartObserving(connectableObservable), ActorRef.noSender());
//        roomActor.tell(new StartObserving(connectableObservable), ActorRef.noSender());
//
//        connectableObservable.connect();
//
//        system.terminate();
//    }
//
//    public static class StartObserving {
//        public final ConnectableObservable<Integer> observable;
//
//        public StartObserving(ConnectableObservable<Integer> observable) {
//            this.observable = observable;
//        }
//    }
//
//    public static class PubSubChannel extends AbstractActor {
//        @Override
//        public Receive createReceive() {
//            return receiveBuilder()
//                    .match(StartObserving.class, msg -> {
//                        msg.observable.subscribe(n -> System.out.println("PubSubChannel received: " + n));
//                    })
//                    .build();
//        }
//    }
//
//    public static class RoomActor extends AbstractActor {
//        @Override
//        public Receive createReceive() {
//            return receiveBuilder()
//                    .match(StartObserving.class, msg -> {
//                        msg.observable.subscribe(n -> System.out.println("RoomActor received: " + n));
//                    })
//                    .build();
//        }
//    }
//}
