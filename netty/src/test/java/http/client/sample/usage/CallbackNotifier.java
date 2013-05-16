package http.client.sample.usage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CallbackNotifier {
	private final CountDownLatch countDownlatch;
	
	public CallbackNotifier() {
		this.countDownlatch = new CountDownLatch(1);
	}
	public void notifiyWaiter(){
		countDownlatch.countDown();
	}
	
	public boolean await(int waitTime, TimeUnit seconds) throws InterruptedException {
		return countDownlatch.await(waitTime, seconds);
	}
}
