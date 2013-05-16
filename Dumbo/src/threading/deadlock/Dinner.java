package threading.deadlock;

import java.lang.management.ThreadInfo;
import java.util.Collection;

public class Dinner {
	public static void main(String[] args) throws Exception {
		final Philosopher[] philosophers = new Philosopher[5];
		Object[] chopsticks = new Object[philosophers.length];
		for (int i = 0; i < chopsticks.length; i++) {
			chopsticks[i] = new Object();
		}
		for (int i = 0; i < philosophers.length; i++) {
			Object left = chopsticks[i];
			Object right = chopsticks[(i + 1) % chopsticks.length];
//			philosophers[i] = new Philosopher(left, right);
			if(i<philosophers.length-2){
				philosophers[i] = new Philosopher(left, right);
			}else{
				philosophers[i] = new Philosopher(left, right){
					@Override
					public void run() {
						try {
							while (true) {
								ponder(); // thinking
								synchronized (right) {
									ponder();
									synchronized (left) {
										ponder(); // eating
									}
								}
							}
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
							return;
						}
					}
				};
			}
			Thread t = new Thread(philosophers[i], "Phil " + (i + 1));
			t.start();
		}

		DeadlockChecker checker = new DeadlockChecker();
		while (true) {
			Collection<ThreadInfo> threads = checker.check();
			if (!threads.isEmpty()) {
				System.out.println("Found deadlock:");
				for (ThreadInfo thread : threads) {
					System.out.println("\t" + thread.getThreadName());
				}
				System.exit(1);
			}
			Thread.sleep(1000);
		}
	}
}
