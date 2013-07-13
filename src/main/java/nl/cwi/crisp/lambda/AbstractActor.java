package nl.cwi.crisp.lambda;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AbstractActor implements Actor {
	
	private final BlockingQueue<Runnable> q = new LinkedBlockingQueue<>();
	
	public AbstractActor() {
	}
	
	@Override
	public BlockingQueue<Runnable> getQueue() {
		return q;
	}
	
}
