package learn.nio.listener;

import java.nio.ByteBuffer;

import learn.nio.listener.impl.GettableQueue;

public interface ChannelFacade {
	GettableQueue<ByteBuffer> getInputQueue();
}
