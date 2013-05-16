package learn.nio.listener.impl;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import learn.nio.listener.ClientHandler;


public final class Acceptor implements Runnable {
	private final NIOServerDispatcherImpl dispatcher;
	private final SelectionKey key;
	
	public Acceptor(SelectionKey key, NIOServerDispatcherImpl dispatcher) {
		this.dispatcher = dispatcher;
		this.key = key;
	}
	
	@Override
	public void run() {
		try {
			ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
			SocketChannel socketChannel = serverSocketChannel.accept();
			socketChannel.configureBlocking(false);
			
			HandlerAdapter attachment = new HandlerAdapter(7, (ClientHandler) key.attachment());
			SelectionKey registeredkey = dispatcher.register(socketChannel, SelectionKey.OP_READ, attachment);
			attachment.setKey(registeredkey);
			
			System.out.println("Got connection from["+socketChannel+"]");

		} catch (IOException e) {
			Thread.currentThread().interrupt();
		}finally{
		}
	}
}