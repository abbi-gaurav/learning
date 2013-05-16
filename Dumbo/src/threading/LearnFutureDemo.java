package threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


public class LearnFutureDemo {
	private static final int MAX_VALUE = 300000000;

	public static int amountDivisibleBy(int first, int last, int divisor){
		int amount = 0;
		for(int i = first; i <= last;i++){
			if((i % divisor) == 0){
				amount++;
			}
		}
		return amount;
	}
	
	public static int amountDivisibleByFuture(final int first, final int last, final int divisor) 
	throws InterruptedException, ExecutionException{
		int threadCount = 4;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount );
		List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
		
		final int step = (last-first)/threadCount;
		for(int i = 0;i<threadCount;i++){
			final int start = first + ((step * i) + i);
			FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					return LearnFutureDemo.amountDivisibleBy(start, 
							(start+step) > last ? last : start+step, divisor);
				}
			});
			taskList.add(task);
			executor.execute(task);
		}
		
		int amount = 0;
		for(FutureTask<Integer> task:taskList){
			amount += task.get();
		}
		executor.shutdown();
		return amount;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		run();
		run();
		run();
		run();
		run();
	}

	public static void run() throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		long result = amountDivisibleBy(0, MAX_VALUE, 3);
		long end = System.currentTimeMillis();
		System.out.println("Result["+result+"] in ["+(end-start)+"] ms");
		
		long startFuture = System.currentTimeMillis();
		long resultFuture = amountDivisibleByFuture(0, MAX_VALUE, 3);
		long endFuture = System.currentTimeMillis();
		System.out.println("Result["+resultFuture+"] in ["+(endFuture-startFuture)+"] ms");
	}
}
