package nl.cwi.crisp.lambda;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;

public class Monitors {

	private static final MetricRegistry registry = new MetricRegistry();
	private static final CsvReporter REPORTER = new CsvReporter(registry);

	public static void init() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				REPORTER.report();
			}
		});
		REPORTER.start(10, TimeUnit.SECONDS);
		
		Timer terminator = new Timer();
		terminator.schedule(new TimerTask() {
			@Override
			public void run() {
				System.exit(0);
			}
		}, TimeUnit.SECONDS.toMillis(305));
	}

	private Monitors() {
	}

	public static void registerCodahale(final Actor o, Metric m) {
		String name = MetricRegistry.name(o.toString(), "qsize");
		registry.register(name, m);
	}

}
