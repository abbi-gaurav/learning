package learn.nio.netty.server;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import learn.nio.netty.CommonUtils;
import learn.nio.netty.utils.Constants;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeServerHandler extends SimpleChannelHandler {
	public static final Logger LOGGER = LoggerFactory.getLogger(TimeServerHandler.class);
	private final long waitInSeconds;
	
	public TimeServerHandler(long waitInSeconds) {
		this.waitInSeconds = waitInSeconds;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext channelHandlerCtx, MessageEvent event) throws Exception {
		doOperation(event);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		LOGGER.error("error in server", e.getCause());
		Channel channel = e.getChannel();
		closeChannel(channel);
	}

	private void closeChannel(Channel channel) throws InterruptedException {
		ChannelFuture future = channel.close();
		
		CountDownLatch latch = new CountDownLatch(1);
		future.addListener(new ChannelOperationFuture(latch));
		if(latch.await(waitInSeconds, TimeUnit.SECONDS)){
			LOGGER.info("Successfully able to close the channel {}", new Object[] { channel.toString() });
		}
	}
	
	private void doOperation(MessageEvent event) throws InterruptedException {
		String integerAsString = (String) event.getMessage();
		int integer = Integer.parseInt(integerAsString);
		LOGGER.debug("Got number {}", CommonUtils.getLoggableArgs(integer));
		
		Double sqrt = Math.sqrt(integer);
		byte[] responseBytes = sqrt.toString().getBytes();
		ChannelBuffer responseBuffer = ChannelBuffers.buffer(responseBytes.length+2);
		responseBuffer.writeBytes(responseBytes);
		responseBuffer.writeByte(Constants.CR);
		responseBuffer.writeByte(Constants.LF);
		LOGGER.debug("Writing sqrt {}", CommonUtils.getLoggableArgs(sqrt));
		Channel channel = event.getChannel();
		channel.write(responseBuffer);
		closeChannel(channel);
	}
}
