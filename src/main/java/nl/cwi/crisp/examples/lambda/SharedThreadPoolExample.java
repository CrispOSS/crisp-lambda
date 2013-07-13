package nl.cwi.crisp.examples.lambda;

import nl.cwi.crisp.lambda.Monitors;


public class SharedThreadPoolExample {

	public static void main(String[] args) {
		Monitors.init();
		for (int i = 0; i < 15000; i++) {
			final SharedThreadPoolPing ping = new SharedThreadPoolPing();
			final SharedThreadPoolPong pong = new SharedThreadPoolPong(ping);
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
