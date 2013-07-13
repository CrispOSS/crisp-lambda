package nl.cwi.crisp.examples.lambda;

import nl.cwi.crisp.lambda.Monitors;

public class DedicatedThreadExample {

	public static void main(String[] args) {
		Monitors.init();
		for (int i = 0; i < 15000; i++) {
			final ThreadPing ping = new ThreadPing();
			final ThreadPong pong = new ThreadPong(ping);
			ping.setPong(pong);

			new Thread() {
				@Override
				public void run() {
					ping.start();
					pong.start();
					ping.ping();
				}
			}.start();
		}

	}
}
