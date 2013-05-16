package learn.nio.byteBuffer;

import java.nio.ByteBuffer;

import threading.utils.ExtensiblePool;

public class ByteBufferPoolManager {

	public static final int POOL_SIZE = 7;

	private static ExtensiblePool<ByteBuffer> BYTE_BUFFER_POOL = new ExtensiblePool<ByteBuffer>(ByteBufferCreator.get(), POOL_SIZE,
	POOL_SIZE * 16);

	public static ExtensiblePool<ByteBuffer> get() {
		return BYTE_BUFFER_POOL;
	}

}
