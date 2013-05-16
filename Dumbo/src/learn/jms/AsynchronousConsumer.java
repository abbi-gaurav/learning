package learn.jms;

import java.util.concurrent.CountDownLatch;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.NamingException;

public class AsynchronousConsumer extends SynchronousConsumer{
	
	public AsynchronousConsumer(String destinationName,
					boolean isTransactional, CountDownLatch latch) throws NamingException {
		super(destinationName,isTransactional,latch);
	}
	@Override
	public MessageConsumer getConsumer(Session session) throws JMSException {
		MessageConsumer consumer = super.getConsumer(session);
		MessageListener messageListener = new MyMessageListener(session);
		consumer.setMessageListener(messageListener);
		
		return consumer;
	}
	
	@Override
	public int consumeMessage(MessageConsumer consumer) throws JMSException {
		//do nothing here for asynchronous consumer
		return 0;
	}
	
	@Override
	protected void finishClientTask() {
		sleepForProducer();
		super.finishClientTask();
	}
	protected void sleepForProducer() {
		while(latch.getCount() > 1){
			try {
				Thread.sleep(4*1000);
			} catch (InterruptedException e) {
				MyLogger.LOGGER.error("Sleep interrupted", e);
			}
		}
	}
	
	protected void commitTransactional(Session session) throws JMSException {
		//do nothing here for asynchronous consumer
	}
	
	@Override
	protected Session getSession() throws JMSException {
		return BasicJMS.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
}
