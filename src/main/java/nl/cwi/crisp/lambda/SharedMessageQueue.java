package nl.cwi.crisp.lambda;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

	private final ScheduledExecutorService E = Executors.newScheduledThreadPool(8);
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
		E.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				boolean empty = true;
				for (Actor a : actors) {
					empty = a.getQueue().isEmpty() && empty;
				}
				if (empty) {
					E.shutdownNow();
				}
			}
		}, 0, 10, TimeUnit.SECONDS);
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
