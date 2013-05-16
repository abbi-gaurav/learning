package learn.axis2.client;

import java.util.concurrent.CountDownLatch;

import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.context.MessageContext;

public final class MyAxisCallback implements AxisCallback {
	private final CountDownLatch latch;

	public MyAxisCallback(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void onMessage(MessageContext msgContext) {
		System.out.println(msgContext.getEnvelope().getBody().getFirstElement());
	}

	@Override
	public void onFault(MessageContext msgContext) {
		System.out.println(msgContext.getEnvelope());
	}

	@Override
	public void onError(Exception e) {
		e.printStackTrace();
	}

	@Override
	public void onComplete() {
		latch.countDown();
	}
}