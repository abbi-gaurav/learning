package threading.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestPhaser {
	
	public static void main(String[] args) throws InterruptedException, TimeoutException {
		final Phaser phaser = new Phaser(1);
		
		testPhase(phaser);
		testPhase(phaser);
		testPhase(phaser);
	}

	private static void testPhase(final Phaser phaser)
			throws InterruptedException, TimeoutException {
		phaser.register();
		
		new Thread(){
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				phaser.arriveAndDeregister();
				System.out.println("::"+phaser.getUnarrivedParties()+"::"+phaser.getPhase());
			};
		}.start();
		
		int arrive = phaser.arrive();
		System.out.println(arrive);
		phaser.awaitAdvanceInterruptibly(arrive, 4, TimeUnit.SECONDS);
		System.out.println("await over");
	}
}
