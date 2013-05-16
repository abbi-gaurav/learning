package learn.nio.listener.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import learn.nio.listener.ClientHandler;
import learn.nio.listener.NIOServerDispatcher;
import learn.nio.listener.usageExample.ClientHandlerImpl;

public class NIOServerDispatcherImpl implements NIOServerDispatcher{

	private final ExecutorService executorService;
	private final Selector selector;
	private final ReadWriteLock selectorGuard = new ReentrantReadWriteLock();
	
	private final LinkedBlockingQueue<HandlerAdapter> statusChangeQueue = new LinkedBlockingQueue<HandlerAdapter>();
	
	private final int port;
	private final ClientHandler clientHandler;
	
	public NIOServerDispatcherImpl(int port, ClientHandler clientHandler, int threads) throws IOException {
		this.port = port;
		this.clientHandler = clientHandler;
		this.selector = Selector.open();
		this.executorService = Executors.newFixedThreadPool(threads);
	}
	
	/* (non-Javadoc)
	 * @see learn.nio.listener.impl.NIODispatchable#start()
	 */
	@Override
	public void start() throws IOException{
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ServerSocket serverSocket = ssc.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		serverSocket.bind(address);

		register(ssc, SelectionKey.OP_ACCEPT, clientHandler);

		System.out.println("Listening on port["+port+"]");
		dispatch();
	}

	SelectionKey register(AbstractSelectableChannel ssc, int opAccept, Object att) throws ClosedChannelException {
		acquireSelectGuard();
		try{
			return ssc.register(selector, opAccept, att);
		}finally{
			releaseSelectGuard();
		}
	}

	private void dispatch() throws IOException {
		while(true){
			selectorGuardBarrier();
			
			selector.select();
			checkStatusChangeQueue();
			processSelectedKeys();
		}

	}

	private void processSelectedKeys() {
		Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
		while(selectedKeys.hasNext()){
			try{
				SelectionKey key = selectedKeys.next();
				selectedKeys.remove();
				if(key.readyOps() == SelectionKey.OP_ACCEPT){
					new Acceptor(key,this).run();
				}else if (key.readyOps() == SelectionKey.OP_READ){
					invokeHandler((HandlerAdapter) key.attachment());
				}else{
					//TODO: handle
				}
			}finally{
			}
		}
	}
	
	private void invokeHandler(HandlerAdapter adapter) {
		adapter.prepareToRun();
		adapter.key().interestOps(0);
		
		executorService.execute(new HandlerFutureTask(this,adapter));
	}

	private void checkStatusChangeQueue() {
		HandlerAdapter adapter;
		while((adapter = statusChangeQueue.poll()) != null){
			if(adapter.isDead()){
				unregisterChannel(adapter);
			}else{
				resumeSelection(adapter);
			}
		}
	}

	private void resumeSelection(HandlerAdapter adapter) {
		SelectionKey key = adapter.key();
		if(key.isValid()){
			key.interestOps(adapter.getInterestOps());
		}
	}

	private void unregisterChannel(HandlerAdapter adapter) {
		SelectionKey key = adapter.key();
		acquireSelectGuard();
		try{
			key.cancel();
		}finally{
			releaseSelectGuard();
		}
	}

	private void selectorGuardBarrier() {
		selectorGuard.writeLock().lock();
		selectorGuard.writeLock().unlock();
	}
	
	private void releaseSelectGuard() {
		selectorGuard.readLock().unlock();
		selector.wakeup();
	}

	private void acquireSelectGuard() {
		selectorGuard.readLock().lock();
	}
	
	
	void enqueueStatusChange(HandlerAdapter adapter){
		statusChangeQueue.add(adapter);
		try{
			acquireSelectGuard();
		}finally{
			releaseSelectGuard();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new NIOServerDispatcherImpl(2509,new ClientHandlerImpl(), 10).start();
	}
}
