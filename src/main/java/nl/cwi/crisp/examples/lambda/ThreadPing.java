package nl.cwi.crisp.examples.lambda;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import nl.cwi.crisp.lambda.ThreadActor;

public class ThreadPing extends ThreadActor {

	private static final AtomicInteger counter = new AtomicInteger();
	private Integer number;
	private ThreadPong pong;

	public ThreadPing() {
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

	public void setPong(ThreadPong pong) {
		this.pong = pong;
	}
	
	@Override
	public String toString() {
		if (number == null) {
			number = new Integer(counter.incrementAndGet());
		}
		return "Ping-" + number;
	}

}
