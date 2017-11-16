import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

public class Pi {
	public static AtomicLong threadCount = new AtomicLong(0);    
	    
	    public void monteCarloCalc(long num_threads, long num_iterations) {
		//this is the amount of times the thread is allowed to do calculations
	    final long multRatio = num_iterations / num_threads;
		//thread array 
		Thread[] threadsArray = new Thread[(int) num_threads];
		for (int i = 0; i < threadsArray.length; i++) {
		    threadsArray[i] = new Thread(() -> {
			    int currentInteration = 0; //counting the number of times the thread is calculated 
			    int inside = 0; //counting the number of inside random vals
		while (currentInteration < multRatio) {
				currentInteration++;	//increment the iteration number 
				double x = ThreadLocalRandom.current().nextDouble(1);	//generate new random x
				double y = ThreadLocalRandom.current().nextDouble(1);	//generate new random y
				if ((x*x)+(y*y) < 1) 
				    inside++;		//if random value sum is less than 1, increment inside random vals 
			    }
			    threadCount.addAndGet(inside);
			});
		}
		//start each thread inside the thread array
		for (int i = 0; i < threadsArray.length; i++) 
		    threadsArray[i].start();				
		//stop each thread inside the thread array	
		for (int i = 0; i < threadsArray.length; i++) {
		    try {
			threadsArray[i].join();
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
