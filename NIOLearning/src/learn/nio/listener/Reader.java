package learn.nio.listener;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import learn.nio.byteBuffer.ByteBufferPoolManager;


public final class Reader implements Runnable {
	private SelectionKey key;
	
	public Reader(SelectionKey key) {
		this.key = key;
	}
	
	@Override
	public void run() {
		doWork(key);
	}

	public static void doWork(SelectionKey key) {
		ByteBuffer byteBuffer = null;
		SocketChannel socketChannel = null;
		try {
			socketChannel = (SocketChannel) key.channel();
			System.out.println("Got SocketChannel[" + socketChannel + "]");
			
			byteBuffer = ByteBufferPoolManager.get().borrow(25);
			int bytesRead = socketChannel.read(byteBuffer);
			
			if(bytesRead == -1){
				socketChannel.close();
				return;
			}else if(bytesRead == 0){
				return;
			}
			
			byteBuffer.flip();
			byte[] dest = new byte[bytesRead];
			byteBuffer.get(dest, 0, bytesRead);
			System.out.println(new String(dest));
			//socketChannel.write(byteBuffer);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch (IOException e) {
			Thread.currentThread().interrupt();
		}finally{
			if(byteBuffer != null){
				byteBuffer.clear();
				ByteBufferPoolManager.get().giveBack(byteBuffer);
			}
		}
	}
}