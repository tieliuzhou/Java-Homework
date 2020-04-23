package PartI.divisors;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class ThreadedMaxDivisors implements Runnable {
	
	private long min;
	private long max;
	
	public ThreadedMaxDivisors(long min, long max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public void run() {
		
	}
	

	public static void main(String[] args) {
		
		long min = 100_000;
		long max = 200_000;
		
		Set<Thread> threadSet = new HashSet<Thread>();
		Set<ThreadedMaxDivisors> divisorsSet = new HashSet<ThreadedMaxDivisors>();
		long startTime = System.currentTimeMillis();

		long NumOfThread = 10;
		long interval = (max-min)/NumOfThread;
		for(int i = 0; i < NumOfThread ;i++){
			if(i == NumOfThread - 1)
				divisorsSet.add(new ThreadedMaxDivisors(min+i*interval,max));
			else
				divisorsSet.add(new ThreadedMaxDivisors(min+i*interval,min+(i+1)*interval));
		}
		for(ThreadedMaxDivisors tmd : divisorsSet){
			Thread t = new Thread(tmd);
			threadSet.add(t);
		}
		
		/* join() tells a thread to wait until it's complete before the rest of the code and proceed.
		 * if we do that for all the threads, then then we can get the results of the threads once
		 * all of them are done
		 */
		for (Thread t: threadSet) {
			try {
				t.join();
				System.out.print("Done");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// at this point, all threads have been completed, since we
		// called the "join()" method on all the threads we created,
		// which forces the code to wait until the thread is finished
		// before we continue
		
		for (ThreadedMaxDivisors tmd : divisorsSet) {
			// presumably you've recorded the results of
			// each ThreadedMaxDivisors run. Pick
			// the largest number with the largest number of
			// divisors
			
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Threaded elapsed time: " + (endTime - startTime));
		startTime = System.currentTimeMillis();
		Entry<Long,Long> e = CountDivisors.maxDivisors(min, max);
		
		long number = e.getKey();
		long numDivisors = e.getValue();
		
		System.out.println("\n" + number + ": " + numDivisors);
		endTime = System.currentTimeMillis();
		
		System.out.println("Non-threaded elapsed time: " + (endTime - startTime));
		
		
		
		
	}
}
