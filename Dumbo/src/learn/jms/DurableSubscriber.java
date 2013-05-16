package learn.jms;

import java.util.concurrent.CountDownLatch;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.NamingException;

public class DurableSubscriber extends SynchronousConsumer {

	public static final String DURABLE_SUBSCRIBER_CLIENT_ID = "durableSubscriber";

	public DurableSubscriber(String destinationName, boolean isTransactional,
			CountDownLatch latch) throws NamingException {
		super(destinationName, isTransactional, latch);
	}
	
	@Override
	public void run() {
		try{
			super.run();
			runSecondConnection();
			
		}catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			MyLogger.LOGGER.error("Interrupted while in durable sleep", e);
		} catch (JMSException e) {
			Thread.currentThread().interrupt();
			MyLogger.LOGGER.error("JMS Error in durable subscriber", e);
		}
	}

	protected void runSecondConnection() throws JMSException,
	InterruptedException {
		Thread t = new Thread(new Runnable(){		
			public void run() {
				Connection connection;
				try {
					BasicJMS.DURABLE_LATCH.await();
					connection = BasicJMS.getCONNECTION_FACTORY().createConnection();
					connection.setClientID(DURABLE_SUBSCRIBER_CLIENT_ID);
					Session session = connection.createSession(isTransactional, Session.AUTO_ACKNOWLEDGE);
					setDurable(session);
					MessageConsumer consumer = getConsumer(session);
					connection.start();
					consumeMessage(consumer);
					connection.close();
				} catch (JMSException e) {
					Thread.currentThread().interrupt();
					MyLogger.LOGGER.error("Error in durable second run",e);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					MyLogger.LOGGER.error("Interrupted Error in durable second run",e);
				}
				
			}
		});
		t.start();
	}
	
	@Override
	protected Session getSession() throws JMSException {
		Session session = super.getSession();
		setDurable(session);
		return session;
	}

	protected void setDurable(Session session) throws JMSException {
		session.createDurableSubscriber((Topic) destination, DURABLE_SUBSCRIBER_CLIENT_ID);
	}
	
	@Override
	public int consumeMessage(MessageConsumer consumer) throws JMSException {
		Message message;
		int count = 10;
		while(count > 0){
			message = consumer.receive(1000);
			if(message == null){
				break;
			}
			count--;
			workWithMessage(message);
		}
		MyLogger.LOGGER.info("["+count+"] messages recieved");
		return count;
	}
	
}
