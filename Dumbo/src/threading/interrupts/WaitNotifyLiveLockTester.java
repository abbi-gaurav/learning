package threading.interrupts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WaitNotifyLiveLockTester {
	public static void main(String[] args) throws Exception {
		final WaitNotifyLiveLock wnll = new WaitNotifyLiveLock();
		
		ExecutorService pool = Executors.newCachedThreadPool();
		
		Future<?> wait4Future = pool.submit(new Runnable() {
			@Override
			public void run() {
				wnll.waitFor();
			}
		});
		
		while(WaitNotifyLiveLock.wtngThread == null){
			Thread.sleep(10);
		}
		
		WaitNotifyLiveLock.wtngThread.interrupt();
		
		Future<?> notifyFuture = pool.submit(new Runnable() {
			
			@Override
			public void run() {
				wnll.notifyIt();
			}
		});
		
		try{
			notifyFuture.get(1, TimeUnit.SECONDS);
		}catch(TimeoutException e){
			System.out.println("notify does not complete");
		}
		
		try{
			wait4Future.get(1, TimeUnit.SECONDS);
		}catch(TimeoutException e){
			System.out.println("wait 4 future does not complete");
			System.out.println("Waiting thread state: " +
			          WaitNotifyLiveLock.wtngThread.getState());
		}
	}
}
