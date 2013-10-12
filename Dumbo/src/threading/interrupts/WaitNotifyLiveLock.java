package threading.interrupts;

public class WaitNotifyLiveLock {
	private final Object lock = new Object();
	public static volatile Thread wtngThread;
	private boolean state = false;

	public void waitFor(){
		synchronized (lock ) {
			wtngThread = Thread.currentThread();
			
			while(!state ){
				try{
					lock.wait();
				}catch(InterruptedException e){
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	public void notifyIt(){
		synchronized (lock) {
			state = true;
			lock.notifyAll();
		}
	}
}
