package learn.nio.netty.server;

import java.util.concurrent.CountDownLatch;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;

public class ChannelOperationFuture implements ChannelFutureListener {
	
	private final CountDownLatch latch;

	public ChannelOperationFuture(CountDownLatch latch) {
		this.latch = latch;
	}

	public void operationComplete(ChannelFuture future) throws Exception {
		//TODO:add any specific steps if required
		latch.countDown();
	}
}