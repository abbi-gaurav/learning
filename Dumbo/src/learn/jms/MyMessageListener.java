package learn.jms;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

public class MyMessageListener implements MessageListener {
	private static final AtomicInteger COUNT = new AtomicInteger();
	private final Session jmsSession;
	
	public MyMessageListener(Session jmsSession) {
		this.jmsSession = jmsSession;
	}
	
	@Override
	public void onMessage(Message message) {
		try {
			COUNT.incrementAndGet();
			System.out.println(this+" Message Recieved["+COUNT+"] for jms Session["+jmsSession+"]");
			AsynchronousConsumer.workWithMessage(message);
		} catch (JMSException e) {
			MyLogger.LOGGER.error("Error getting text",e);
		}
	}

	public static int getCOUNT() {
		return COUNT.get();
	}
}