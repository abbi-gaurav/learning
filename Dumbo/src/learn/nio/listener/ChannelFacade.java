package learn.nio.listener;

import java.nio.ByteBuffer;

public interface ChannelFacade {
	GettableQueue<ByteBuffer> getInputQueue();
}
