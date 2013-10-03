package nl.cwi.crisp.examples.lambda;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
public class Benchmarks {
	
	public Benchmarks() {
	}
	
	@GenerateMicroBenchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Warmup(iterations = 100)
	@Measurement(iterations = 10000)
	public void dedicated10000() {
		ThreadPing ping = new ThreadPing();
		ThreadPong pong = new ThreadPong(ping);
		ping.setPong(pong);

		ping.start();
		pong.start();
		ping.ping();
	}
	
//	@GenerateMicroBenchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Warmup(iterations = 100)
	@Measurement(iterations = 5000)
	public void dedicated5000() {
		ThreadPing ping = new ThreadPing();
		ThreadPong pong = new ThreadPong(ping);
		ping.setPong(pong);

		ping.start();
		pong.start();
		ping.ping();
	}
	
//	@GenerateMicroBenchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Warmup(iterations = 100)
	@Measurement(iterations = 1000)
	public void dedicated1000() {
		ThreadPing ping = new ThreadPing();
		ThreadPong pong = new ThreadPong(ping);
		ping.setPong(pong);

		ping.start();
		pong.start();
		ping.ping();
	}
	
//	@GenerateMicroBenchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Warmup(iterations = 100)
	@Measurement(iterations = 1000)
	public void shared1000() {
		SharedThreadPoolPing ping = new SharedThreadPoolPing();
		SharedThreadPoolPong pong = new SharedThreadPoolPong(ping);
		ping.setPong(pong);
		
		ping.start();
		pong.start();
		ping.ping();
	}
	
//	@GenerateMicroBenchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Warmup(iterations = 100)
	@Measurement(iterations = 5000)
	public void shared5000() {
		SharedThreadPoolPing ping = new SharedThreadPoolPing();
		SharedThreadPoolPong pong = new SharedThreadPoolPong(ping);
		ping.setPong(pong);
		
		ping.start();
		pong.start();
		ping.ping();
	}
	
//	@GenerateMicroBenchmark
	@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
	@OutputTimeUnit(TimeUnit.SECONDS)
	@Warmup(iterations = 100)
	@Measurement(iterations = 10000)
	public void shared10000() {
		SharedThreadPoolPing ping = new SharedThreadPoolPing();
		SharedThreadPoolPong pong = new SharedThreadPoolPong(ping);
		ping.setPong(pong);
		
		ping.start();
		pong.start();
		ping.ping();
	}
	
	public static void main(String[] args) {
		Main.main(new String[] {});
	}

}
