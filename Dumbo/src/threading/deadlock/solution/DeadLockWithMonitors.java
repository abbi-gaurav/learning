package threading.deadlock.solution;

public class DeadLockWithMonitors implements DeadLockingClass{
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	@Override
	public void method1() throws InterruptedException {
		synchronized (lock1) {
			Thread.sleep(1*1000);
			synchronized (lock2) {
			}
		}
		
	}
	@Override
	public void method2() throws InterruptedException {
		synchronized (lock2) {
			Thread.sleep(1*1000);
			synchronized (lock1) {
			}
		}
	}
}
