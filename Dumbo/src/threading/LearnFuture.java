package threading;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LearnFuture {
	public static void main(String[] args){
		ExecutorService service = Executors.newSingleThreadExecutor();
		final MyTask registration = new MyTask();
		Future<String> future = service.submit(registration);
		String string = null;
		
		Thread workflow = new Thread() {
			@Override
			public void run() {
				try {
					//work
					Thread.sleep(10 * 1000);
					registration.set();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		try {
			System.out.println("starting BP");
			workflow.start();
			string = future.get(7, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("interrupted");
		} catch (ExecutionException e) {
			System.out.println("execution failed");
		} catch (TimeoutException e) {
			System.out.println("timeout");
			future.cancel(true);
		}
		service.shutdown();
		System.out.println(string);
	}
}

class MyTask implements Callable<String>{
	private boolean isSet;
	
	@Override
	public synchronized String call(){
		while(!(Thread.currentThread().isInterrupted()) && !isSet){
			try {
				wait(5 * 1000);
			} catch (InterruptedException e) {
				System.out.println("task interrupted");
				Thread.currentThread().interrupt();
			}
			
		}
		return "waiting is over";
	}
	
	public synchronized void set(){
		System.out.println("respond service called");
		isSet = true;
		notify();
	}
	
}