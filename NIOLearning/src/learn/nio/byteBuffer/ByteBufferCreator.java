package learn.nio.byteBuffer;

import java.nio.ByteBuffer;

import threading.utils.PoolItemCreator;

public final class ByteBufferCreator implements PoolItemCreator<ByteBuffer> {
	private static final ByteBufferCreator singleton = new ByteBufferCreator();
	private static final int CAPACITY = 1024;
	
	private ByteBufferCreator(){}
	
	public static ByteBufferCreator get() {
		return singleton;
	}

	@Override
	public ByteBuffer create() {
		return ByteBuffer.allocate(ByteBufferCreator.CAPACITY);
	}
}