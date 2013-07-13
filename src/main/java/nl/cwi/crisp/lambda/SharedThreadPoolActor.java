package nl.cwi.crisp.lambda;

import java.util.concurrent.BlockingQueue;

import com.codahale.metrics.Counter;

public class SharedThreadPoolActor extends AbstractActor {
	
	private final static SharedMessageQueue QUEUE = new SharedMessageQueue();
	private final Counter counter = new Counter();
	
	public SharedThreadPoolActor() {
		Monitors.registerCodahale(this, counter);
		QUEUE.register(this);
	}
	
	@Override
	public Runnable deq() {
		synchronized (counter) {
			counter.inc();
		}
		return super.deq();
	}
	
	@Override
	public BlockingQueue<Runnable> getQueue() {
		return QUEUE;
	}
	
}
