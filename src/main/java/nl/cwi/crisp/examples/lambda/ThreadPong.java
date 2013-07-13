package nl.cwi.crisp.examples.lambda;

import java.util.concurrent.atomic.AtomicInteger;

import nl.cwi.crisp.lambda.ThreadActor;

public class ThreadPong extends ThreadActor {
	
	private static final AtomicInteger counter = new AtomicInteger();
	private Integer number;

	private ThreadPing ping;

	public ThreadPong(ThreadPing ping) {
		this.ping = ping;
	}

	public void pong(final String s) {
		send(ping, () -> {
//				if ("exit".equals(s)) {
//					System.exit(0);
//				}
				ping.ping();
		});
	}
	
	@Override
	public String toString() {
		if (number == null) {
			number = new Integer(counter.incrementAndGet());
		}
		return "Pong-" + number;
	}
}
