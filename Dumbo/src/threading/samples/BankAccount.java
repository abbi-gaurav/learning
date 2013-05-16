package threading.samples;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccount {
	private volatile int balance;
	private final  ReadWriteLock lock = new ReentrantReadWriteLock();
	public BankAccount(int balance) {
		this.balance = balance;
	}
	public void deposit(int amount) throws InterruptedException {
		lock.writeLock().lockInterruptibly();
		try{
			int check = balance + amount;
			balance = check;
			if (balance != check) {
				throw new AssertionError("Data Race Detected");
			}
			//		System.out.println("deposit Thread["+Thread.currentThread().getId()+" balance " +
			//				"["+balance+"]");
		}finally{
			lock.writeLock().unlock();
		}
	}
	public void withdraw(int amount) throws InterruptedException {
		deposit(-amount);
//		System.out.println("withdraw Thread["+Thread.currentThread().getId()+" balance " +
//				"["+balance+"]");
	}
	public int getBalance() throws InterruptedException {
		lock.readLock().lockInterruptibly();
		try{
			return balance; 
		}finally{
			lock.readLock().unlock();
		}
	}
}