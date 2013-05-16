package learn.nio.listener.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learn.nio.byteBuffer.ByteBufferPoolManager;
import learn.nio.listener.ChannelFacade;
import learn.nio.listener.ClientHandler;

public class HandlerAdapter implements Callable<HandlerAdapter>,ChannelFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(HandlerAdapter.class);
	private SelectionKey key;
	private final Object stateChangeLock = new Object();
	private final GettableQueue<ByteBuffer> inputQueue;
	
	private volatile boolean running;
	private volatile boolean shuttingDown;
	private volatile int interestOps;
	private volatile boolean dead;
	
	private final ClientHandler clientHandler;
	
	public HandlerAdapter(int queueSize,ClientHandler handler) {
		this.clientHandler = handler;
		this.inputQueue = new GettableQueue<ByteBuffer>(queueSize);
	}
	
	public boolean isDead() {
		return dead;
	}

	public void prepareToRun() {
		synchronized (stateChangeLock) {
			this.running = true;
			this.interestOps = key.interestOps();
		}
	}

	@Override
	public HandlerAdapter call() throws Exception {
		try{
			fillInput();
			return this;
		}finally{
			synchronized (stateChangeLock) {
				running = false;
			}
		}
	}

	private void fillInput() throws InterruptedException, IOException {
		if(key.isReadable()  && inputQueue.notFull()){
			readFromChanel();
		}
		
		if(inputQueue.notEmpty()){
			disableReadSelection();
		}
		
		if(shuttingDown){
			key.channel().close();
		}
	}

	private void disableReadSelection() {
		//TODO: need to implement this
	}

	private void readFromChanel() throws InterruptedException, IOException {
		ByteBuffer byteBuffer = ByteBufferPoolManager.get().borrow(25);
		ReadableByteChannel channel = (ReadableByteChannel) key.channel();
		int bytesRead = channel.read(byteBuffer);
		
		LOGGER.debug("Bytes Read from Input Channel {}", new Object[]{bytesRead});
		
		if(bytesRead == -1){
			shuttingDown = true;
		}
		byteBuffer.flip();
		inputQueue.put(byteBuffer);
		clientHandler.handleInput(this);
	}

	@Override
	public GettableQueue<ByteBuffer> getInputQueue() {
		return this.inputQueue;
	}

	public void die() {
		this.dead = true;
	}

	public SelectionKey key() {
		return key;
	}
	
	void setKey(SelectionKey key){
		this.key = key;
	}

	public int getInterestOps() {
		return interestOps;
	}
}
