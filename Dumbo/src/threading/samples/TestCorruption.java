package threading.samples;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class TestCorruption {
	private static final int THREADS = 2;
	private static final CountDownLatch latch =
		new CountDownLatch(THREADS);
	private static final BankAccount heinz =
		new BankAccount(1000);

	public static void main(String[] args) {
		for (int i = 0; i < THREADS; i++) {
			addThread(true).start();
		}
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			public void run() {
				try {
					System.out.println("Balance:["+heinz.getBalance()+"]");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 5, 1000);
	}

	private static Thread addThread(final boolean loop) {
		return new Thread() {
			public void run() {
				latch.countDown();
				try {
					latch.await();
				} catch (InterruptedException e) {
					return;
				}
				while (loop) {
					try {
						heinz.withdraw(100);
						heinz.deposit(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
}