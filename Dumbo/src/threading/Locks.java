package threading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Locks {
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private Lock readLock = rwLock.readLock();
	private Lock writeLock = rwLock.writeLock();
	
	public int getData(){
		try{
			readLock.lockInterruptibly();
			//read
			return 0;
		}catch(InterruptedException ie){
			Thread.currentThread().interrupt();
			throw new RuntimeException(ie);
		}finally{
			readLock.unlock();
		}
	}
	
	public void writeData(int data){
		try{
			writeLock.lockInterruptibly();
			//write data
		}catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}
}
