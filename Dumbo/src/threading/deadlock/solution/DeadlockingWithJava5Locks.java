package threading.deadlock.solution;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockingWithJava5Locks implements DeadLockingClass{
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	
	@Override
	public void method1() throws InterruptedException {
		lock1.lock();
		try{
			Thread.sleep(1000);
			lock2.lock();
			try{
			}finally{
				lock2.unlock();
			}
		}finally{
			lock1.unlock();
		}
	}

	@Override
	public void method2() throws InterruptedException {
		lock2.lock();
		try{
			Thread.sleep(1000);
			lock1.lock();
			try{
			}finally{
				lock1.unlock();
			}
		}finally{
			lock2.unlock();
		}
	}
}
