package threading.deadlock.solution;

import java.lang.management.ThreadInfo;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import threading.deadlock.DeadlockChecker;

public class DeadlockTest {
	private static final DeadlockChecker checker = new DeadlockChecker();
	private static final DeadLockingClass[] deadlockinClasses = {
//		new DeadLockWithMonitors(),
//		new DeadlockingWithJava5Locks(),
		new DeadlockingWithJava5LocksInterrupt()
	};
	
	public static void main(String[] args) {
		DeadlockTest testObject = new DeadlockTest();
		for(DeadLockingClass deadLockingClass:deadlockinClasses){
			System.out.println("================================================================");
			String name = deadLockingClass.getClass().getSimpleName();
			System.out.println(name);
			System.out.println("================================================================");
			testObject.resolveByStop(deadLockingClass);
			
			System.out.println("---------------------------------------------");
			testObject.resolveByInterrupt(deadLockingClass);
		}
	}

	private void resolveByInterrupt(DeadLockingClass deadLockingClass) {
		System.out.println("testing resolve by interrupt");
		Thread threads[] = setupThreads(deadLockingClass,"t3","t4");
		sleepQuietly(2);
		check();
		System.out.println("Trying to resolve by t3 interrupt");
		threads[0].interrupt();
		sleepQuietly(1);
		check();
		
		System.out.println("Trying to resolve by t4 interrupt");
		threads[1].interrupt();
		sleepQuietly(1);
		check();
		
		checker.ignoreThreads(threads);
	}

	@SuppressWarnings("deprecation")
	private void resolveByStop(DeadLockingClass deadLockingClass) {
		System.out.println("testing resolve by stop");
		Thread threads[] = setupThreads(deadLockingClass,"t1","t2");
		sleepQuietly(2);
		check();
		System.out.println("Trying to resolve by t1 stop");
		threads[0].stop();
		sleepQuietly(1);
		check();
		
		System.out.println("Trying to resolve by t2 stop");
		threads[1].stop();
		sleepQuietly(1);
		check();
		
		checker.ignoreThreads(threads);
	}
	
	private final static void check() {
		printInfo(checker.check());
	}
	
	private static final void printInfo(Collection<ThreadInfo> check) {
		if(check.isEmpty()){
			System.out.println("**** No deadlocks detected");
		}else{
			System.out.println("$$$$ There is a deadlock");
			for (ThreadInfo threadInfo : check) {
				System.out.println(threadInfo.getThreadName()+" and id--"+threadInfo.getThreadId());
			}
		}
	}

	private void sleepQuietly(int i) {
		try{
			TimeUnit.SECONDS.sleep(i);
		}catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private Thread[] setupThreads(final DeadLockingClass deadLockingClass,
			final String name1, String name2) {
		Thread threads[] = new Thread[]{
				new Thread(name1){
					public void run() {
						try {
							deadLockingClass.method1();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							System.out.println(getName()+" interrupted");
						}finally{
							System.out.println(getName()+"--- resolved");
						}
					};
				},
				new Thread(name2){
					public void run() {
						try {
							deadLockingClass.method2();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							System.out.println(getName()+" interrupted");
						}finally{
							System.out.println(getName()+"--- resolved");
						}
					};
				}
		};
		
		for (Thread thread : threads) {
			//thread.setDaemon(true);
			thread.start();
			System.out.println("started thread--"+thread.getName()+"with id---"+thread.getId());
		}
		return threads;
	}
}
