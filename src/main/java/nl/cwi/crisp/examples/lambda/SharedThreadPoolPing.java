package nl.cwi.crisp.examples.lambda;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import nl.cwi.crisp.lambda.SharedThreadPoolActor;

public class SharedThreadPoolPing extends SharedThreadPoolActor {

	private static final AtomicInteger counter = new AtomicInteger();
	private Integer number;
	private SharedThreadPoolPong pong;
	
	public SharedThreadPoolPing() {
	}

	public void ping() {
		send(pong, () -> {
			if (Math.random() > 0.00001) {
				pong.pong(UUID.randomUUID().toString());
			} else {
				pong.pong("exit");
			}
		});
	}

	public void setPong(SharedThreadPoolPong pong) {
		this.pong = pong;
	}
	
	@Override
	public String toString() {
		if (number == null) {
			number = new Integer(counter.incrementAndGet());
		}
		return "SharedPing-" + number;
	}
}
