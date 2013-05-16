package threading.samples;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sayappa {
	public static void main(String[] args) throws InterruptedException {
		final Lock lock = new ReentrantLock();
		Thread t1 = new Thread(){ 
			public void run(){
//				try {
//				} catch (InterruptedException e1) {
//					Thread.currentThread().interrupt();	
//					e1.printStackTrace();
//				}
				try{
					lock.lock();
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}finally{
					lock.unlock();
				}
			};
		};
		t1.start();
		Thread.sleep(1*1000);
		t1.interrupt();
	}
	
}
