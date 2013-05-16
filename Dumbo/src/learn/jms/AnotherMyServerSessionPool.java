package learn.jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;

import threading.utils.Pool;

public class AnotherMyServerSessionPool extends MyServerSessionPool implements ServerSessionPool{
	private Pool<ServerSession> pool;
	public AnotherMyServerSessionPool(int capacity) throws JMSException {
		super(capacity);
		
	}
	
	@Override
	protected void configurePool(int capacity) throws JMSException {
		List<MyServerSession> list = new ArrayList<MyServerSession>(capacity);
		for(int i = 0;i<capacity;i++){
			Session session = BasicJMS.getConnection()
			.createSession(false, Session.AUTO_ACKNOWLEDGE);
			session.setMessageListener(new MyMessageListener(session));
			list.add(new MyServerSession(session,this));
		}
		pool = new Pool<ServerSession>(list);
	}

	@Override
	public ServerSession getServerSession() throws JMSException {
		try{
			return pool.borrow();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			MyLogger.LOGGER.error("Failed to get Server Session from pool", e);
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void returnServerSession(ServerSession serverSession) {
		try{
			pool.giveBack(serverSession);
		}catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			MyLogger.LOGGER.error("Failed to put Server Session into pool", e);
			throw new RuntimeException(e);
		}
	}
}
