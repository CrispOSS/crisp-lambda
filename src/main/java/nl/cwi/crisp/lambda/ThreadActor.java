package nl.cwi.crisp.lambda;

import com.codahale.metrics.Counter;


public class ThreadActor extends AbstractActor {

	private final Thread thread;
	private final Counter counter = new Counter();

	public ThreadActor() {
		thread = new WorkerThread();
		Monitors.registerCodahale(this, counter);
	}
	
	@Override
	public void start() {
		thread.start();
	}

	private class WorkerThread extends Thread {

		public WorkerThread() {
			setName("WorkerThread-" + ThreadActor.this.toString());
		}

		@Override
		public void run() {
			while (true) {
				try {
					Runnable m = deq();
					m.run();
					counter.inc();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

}
