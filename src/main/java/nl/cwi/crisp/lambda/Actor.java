package nl.cwi.crisp.lambda;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Actor is represent by the following behavior:
 * 
 * <ul>
 * <li> {@link #send(Callable)} that accepts incoming messages to an actor
 * <li> {@link #getQueue()} that presents the queue of messages for the actor
 * <li> {@link #start()} that initiates the actor to receive and process messages
 * 
 * @author nobeh
 *
 */
public interface Actor {
	
	/**
	 * Receives a messages into the actor.
	 * 
	 * @param msg the incoming message as an instance of {@link Runnable}
	 */
	default void receive(final Runnable msg) {
		receive(() -> {
			msg.run();
			return null;
		});
	}
	
	/**
	 * Receives a message into the actor and provides the result as a future object.
	 * 
	 * @param msg the incoming message as an instance of {@link Callable}
	 * @param V the type of future value 
	 * @return the {@link Future} holding the result of the message
	 */
	default <V> Future<V> receive(final Callable<V> msg) {
		FutureTask<V> futureMessage = new FutureTask<>(msg);
		enq(futureMessage);
		return futureMessage;
	}
	
	/**
	 * Helper method for {@link #receive(Runnable)}.
	 */
	default void send(Actor a, Runnable msg) {
		a.receive(msg);
	}
	
	/**
	 * Helper method for {@link #receive(Callable)}
	 */
	default <V> Future<V> send(Actor a, Callable<V> msg) {
		return a.receive(msg);
	}
	
	default void enq(final Runnable msg) {
		getQueue().offer(msg);
	}
	
	default Runnable deq() throws RuntimeException {
		try {
			return getQueue().take();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	default void start() {
	}
	
	/**
	 * The queue of messages for the actor.
	 * 
	 * @return
	 */
	BlockingQueue<Runnable> getQueue();

}
