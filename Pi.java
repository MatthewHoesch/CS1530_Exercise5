import java.io.IOException;
import java.util.concurrent.*;
import java.util.*;

public class Pi {
	public static int x = 0;
	public static int y = 0;
	
	//passed arguments
	public static int num_iteration = 0;
	public static int num_threads = 0;
	
	public static int iteration = 0;
	public static int inside = 0;
	
	public static void main(String[] args) throws IOException {
		num_threads = Integer.parseInt(args[0]);
		num_iteration = Integer.parseInt(args[1]);
		
		Thread[] threadArray = new Thread[num_threads];
		
		for (int i = 0; i<num_threads ; i++) {
			threadArray[i] = new Thread(() -> 
				{
					//double x = ThreadLocalRandom.current().nextDouble(0,1);
					//double y = ThreadLocalRandom.current().nextDouble(0,1);
					System.out.println("Inside thread.");
					double x = Math.random();
					double y = Math.random();
					
					if(((x*x)+(y*y)) < 1) {
						inside++;
					}
					iteration++;
				});
		}
		
		
		
		
		for(int i = 0; i < (num_iteration/num_threads); i++) {
			
			for (Thread t : threadArray) t.start();
			
			try {
				for (Thread t : threadArray) t.join();
			} catch (InterruptedException iex) {}	
		}

		int ratio = (inside/num_iteration);
		int pi = ratio*4;
		System.out.println("Total = " + iteration);
		System.out.println("Inside = " + inside);
		System.out.println("Ratio = " + ratio);
		System.out.println("Pi = " + pi);
		
		
		
	}
}
