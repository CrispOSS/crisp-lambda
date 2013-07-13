package nl.cwi.crisp.lambda;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A simple ring implementation of a set of {@link Actor}s to provide an
 * aggregation of the messages of all actors.
 * 
 * @author nobeh
 * 
 */
public class SharedMessageQueue extends LinkedBlockingQueue<Runnable> implements
		BlockingQueue<Runnable> {

	private static final long serialVersionUID = 1L;

	private final ExecutorService E = Executors.newFixedThreadPool(8);
	private final BlockingQueue<Actor> actors = new LinkedBlockingQueue<>();
	private Iterator<Actor> iterator;

	public SharedMessageQueue() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					Runnable msg;
					try {
						msg = doTake();
						if (msg != null) {
							E.submit(msg);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	void register(Actor actor) {
		if (actors.contains(actor)) {
			return;
		}
		actors.add(actor);
	}

	synchronized Runnable doTake() throws InterruptedException {
		if (iterator == null || !iterator.hasNext()) {
			iterator = actors.iterator();
		}
		if (iterator.hasNext()) {
			Runnable r = iterator.next().deq();
			return r;
		}
		return null;
	}

}
