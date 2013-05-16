package threading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class LearnBlockingQueues {
	private static ExecutorService ES = Executors.newFixedThreadPool(2);
	private static int N = 1000000;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		 for (int i = 0; i < 10; i++) {
	            int length = (i == 0) ? 1 : i * 5;
	            System.out.print(length + "\t");
	            System.out.print(doTest(new LinkedBlockingQueue<Integer>(length), N) + "\t");
	            System.out.print(doTest(new ArrayBlockingQueue<Integer>(length), N) + "\t");
	            System.out.print(doTest(new SynchronousQueue<Integer>(), N));
	            System.out.println();
	        }

	        ES.shutdown();
	}
	private static long doTest(final BlockingQueue<Integer> queue,final int n) throws InterruptedException, ExecutionException{
		long t = System.nanoTime();
		ES.submit(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < n; i++) {
					try{
						queue.put(i);
					}catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		});


		Long result = ES.submit(new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				long sum=0;

				for(int i=0;i<n;i++){
					try {
						sum += queue.take();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				return sum;
			}
		}).get();
		
		
		t = System.nanoTime() - t;
		return (long)(1000000000.0 * N/t);
	}
}
