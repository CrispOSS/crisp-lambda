package nl.cwi.crisp.lambda;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Timer;


public class CsvReporter extends ScheduledReporter {
	
	private final Long start = new Long(System.currentTimeMillis());
	private final SortedMap<Long, SortedMap<String, Long>> allCounters = new ConcurrentSkipListMap<Long, SortedMap<String,Long>>();
	private final SortedMap<Long, Long> totalCounts = new ConcurrentSkipListMap<Long, Long>();
	
	public CsvReporter(MetricRegistry registry) {
		super(registry, "csv", MetricFilter.ALL, TimeUnit.SECONDS, TimeUnit.SECONDS);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				doReport();
			}
		});
	}

	@Override
	public void report(SortedMap<String, Gauge> gauges,
            SortedMap<String, Counter> counters,
            SortedMap<String, Histogram> histograms,
            SortedMap<String, Meter> meters,
            SortedMap<String, Timer> timers) {
		Long now = new Long(System.currentTimeMillis() - start.longValue());
		long sum = 0L;
		allCounters.put(now, new ConcurrentSkipListMap<>());
		for (String key : counters.keySet()) {
			long count = counters.get(key).getCount();
			allCounters.get(now).put(key, new Long(count));
			sum  += count;
		}
		totalCounts.put(now, new Long(sum));
	}

	protected void doReport() {
		try {
			File f = new File("./counters.csv");
			f.createNewFile();
			PrintWriter pw = new PrintWriter(f);
//			pw.print("time,");
//			for (Long t : allCounters.keySet()) {
//				for (String name : allCounters.get(t).keySet()) {
//					pw.print(name + ",");
//				}
//			}
//			pw.println();
//			for (Long t : allCounters.keySet()) {
//				pw.print(t + ",");
//				SortedMap<String, Long> timeCounters = allCounters.get(t);
//				for (String name : timeCounters.keySet()) {
//					pw.print(timeCounters.get(name).toString() + ",");
//				}
//				pw.println();
//			}
			pw.println("time,count");
			for (Long time : totalCounts.keySet()) {
				pw.println(TimeUnit.MILLISECONDS.toSeconds(time.longValue()) + "," + totalCounts.get(time));
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
