import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

public class Pi {
	public static AtomicLong threadCount = new AtomicLong(0);    
	    
	    public void monteCarloCalc(long num_threads, long num_iterations) {
	    final long multRatio = num_iterations / num_threads;
		Thread[] threadsArray = new Thread[(int) num_threads];
		
		for (int j = 0; j < threadsArray.length; j++) {
		    threadsArray[j] = new Thread(() -> {
			    int currentCount = 0;
			    int inside = 0;
		while (currentCount++ < multRatio) {
				double x = ThreadLocalRandom.current().nextDouble(1);
				double y = ThreadLocalRandom.current().nextDouble(1);
				if ((x*x)+(y*y) < 1) 
				    inside++;
			    }
			    threadCount.addAndGet(inside);
			});
		}
		
		for (int j = 0; j < threadsArray.length; j++) 
		    threadsArray[j].start();
		for (int j = 0; j < threadsArray.length; j++) {
		    try {
			threadsArray[j].join();
		    } catch (Exception ex) { }
		}
		
		long insideCount = threadCount.get();
		double ratio = (double) insideCount / num_iterations;
		
		System.out.println("Total  = " + num_iterations);
		System.out.println("Inside = " + insideCount);
		System.out.println("Ratio  = " + ratio);
		System.out.println("Pi     = " + (ratio * 4));
 }
	    
	public static void main(String[] args) throws IOException, InterruptedException {
		long num_threads = Long.parseLong(args[0]);
		long num_iteration = Long.parseLong(args[1]);
		
		Pi calculation = new Pi();
		calculation.monteCarloCalc(num_threads, num_iteration);
		
	}
}
