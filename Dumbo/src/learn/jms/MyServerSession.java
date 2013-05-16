package learn.jms;

import javax.jms.JMSException;
import javax.jms.ServerSession;
import javax.jms.Session;

public class MyServerSession implements ServerSession{
		
	private final Session jmsSession;

	private final MyServerSessionPool serverSessionPool;
	
	public MyServerSession(Session jmsSession, MyServerSessionPool myServerSessionPool) {
		this.jmsSession = jmsSession;
		this.serverSessionPool = myServerSessionPool;
	}
	
	@Override
	public Session getSession() throws JMSException {
		return jmsSession;
	}
	private final void returnToPool(){
		serverSessionPool.returnServerSession(this);
	}
	@Override
	public void start() throws JMSException {
		serverSessionPool.submit(new Runnable() {
			@Override
			public void run() {
				jmsSession.run();
				returnToPool();
				
			}
		});
	}
}
