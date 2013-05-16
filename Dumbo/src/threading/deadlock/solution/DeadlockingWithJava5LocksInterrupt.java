package threading.deadlock.solution;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockingWithJava5LocksInterrupt implements DeadLockingClass{
	private Lock lock1 = new ReentrantLock(true);
	private Lock lock2 = new ReentrantLock(true);
	
	@Override
	public void method1() throws InterruptedException {
		lock1.lockInterruptibly();
		try{
			Thread.sleep(1000);
			lock2.lockInterruptibly();
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
		lock2.lockInterruptibly();
		try{
			Thread.sleep(1000);
			lock1.lockInterruptibly();
			try{
			}finally{
				lock1.unlock();
			}
		}finally{
			lock2.unlock();
		}
	}
}
