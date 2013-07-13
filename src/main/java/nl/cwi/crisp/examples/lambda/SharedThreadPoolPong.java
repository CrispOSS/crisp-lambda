package nl.cwi.crisp.examples.lambda;

import java.util.concurrent.atomic.AtomicInteger;

import nl.cwi.crisp.lambda.SharedThreadPoolActor;

public class SharedThreadPoolPong extends SharedThreadPoolActor {

	private static final AtomicInteger counter = new AtomicInteger();
	private Integer number;
	private SharedThreadPoolPing ping;

	public SharedThreadPoolPong(SharedThreadPoolPing ping) {
		this.ping = ping;
	}

	public void pong(final String s) {
		send(ping, () -> {
//			System.out.println("Ping received: " + s);
			ping.ping();
		});
	}
	
	@Override
	public String toString() {
		if (number == null) {
			number = new Integer(counter.incrementAndGet());
		}
		return "SharedPong-" + number;
	}
}

