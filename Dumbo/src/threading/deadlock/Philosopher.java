package threading.deadlock;

public class Philosopher implements Runnable {
	protected final Object left;
	protected final Object right;

	public Philosopher(Object left, Object right) {
		this.left = left;
		this.right = right;
	}

	protected void ponder() throws InterruptedException {
		Thread.sleep(((int) Math.random() * 10) + 10);
	}

	public void run() {
		try {
			while (true) {
				ponder(); // thinking
				synchronized (left) {
					ponder();
					synchronized (right) {
						ponder(); // eating
					}
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}
}
