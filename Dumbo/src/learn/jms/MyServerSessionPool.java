package learn.jms;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;

public class MyServerSessionPool implements ServerSessionPool{
	
	private BlockingQueue<ServerSession> pool;
	protected final ExecutorService serverSessionThreadPool;
	
	public MyServerSessionPool(int poolSize) throws JMSException {
		configurePool(poolSize);
		serverSessionThreadPool = Executors.newFixedThreadPool(poolSize);
	}
	protected void configurePool(int poolSize) throws JMSException {
		pool = new ArrayBlockingQueue<ServerSession>(poolSize);
		for(int i = 0;i<poolSize;i++){
			Session session = BasicJMS.getConnection()
									.createSession(false, Session.AUTO_ACKNOWLEDGE);
			session.setMessageListener(new MyMessageListener(session));
			pool.add(new MyServerSession(session,this));
		}
	}
	@Override
	public ServerSession getServerSession() throws JMSException {
		try {
			return pool.take();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			MyLogger.LOGGER.error("Failed to get Server Session from pool", e);
			throw new RuntimeException(e);
		}
	}
	
	public void returnServerSession(ServerSession serverSession){
		try {
			pool.put(serverSession);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			MyLogger.LOGGER.error("Failed to put Server Session into pool", e);
			throw new RuntimeException(e);
		}
	}
	public void submit(Runnable runnable) {
		serverSessionThreadPool.submit(runnable);
	}
	public void close() {
		serverSessionThreadPool.shutdown();
	}
}
