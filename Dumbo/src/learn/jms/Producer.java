package learn.jms;

import java.util.concurrent.CountDownLatch;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class Producer extends JMSClient implements Runnable {
	public static final String JMS_TYPE = "CurrentTimeInMillis";
	private long runPeriod;
	private static int count;
	
	public Producer(String destinationName,long runPeriod,
				boolean isTransactional, CountDownLatch latch) throws NamingException {
		super(destinationName, isTransactional, latch);
		this.runPeriod = runPeriod;
	}

	@Override
	public void run() {
		try {
			Session session = getSession();
			//session.createQueue("ProducerQueue");
			//printJMSDefProperties();
			final MessageProducer producer = session.createProducer(destination);
			MyLogger.LOGGER.info("Runtime period is["+runPeriod+"]");

			long initialTime = System.currentTimeMillis();
			count = 0;

			while(runPeriod > 0){
				Message message = null;
				message = createMessage(session, ++count);
				message.setJMSType(JMS_TYPE);
				if(count%2 == 0){
					message.setStringProperty("NumberType", "EVEN");
					producer.send(message);
				}else{
					message.setStringProperty("NumberType", "ODD");
					//producer.send(message, DeliveryMode.NON_PERSISTENT,7,300);
					producer.send(message);
				}
				runPeriod = runPeriod-(System.currentTimeMillis() - initialTime);
			}
			MyLogger.LOGGER.info("["+(count)+"]messages sent");
			commitTransactional(session);
		}catch (JMSException e) {
			MyLogger.LOGGER.error("Message Sending failed", e);
			Thread.currentThread().interrupt();
		}catch(Exception ie){
			MyLogger.LOGGER.error("Error during Producer run",ie);
			Thread.currentThread().interrupt();
		}finally{
			finishClientTask();
		}
	}

	protected Message createMessage(Session session, int iLocal)
			throws JMSException {
		Message message;
		switch(iLocal%5){
		case 1:
			message = createTextMessage(session);
			break;
		case 2:
			message = createMapMessage(session);
			break;
		case 3:
			message = createByteMessage(session);
			break;
		case 4:
			message = createObjectMessage(session);
			break;
		case 5:
			message = createStreamMessage(session);
			break;
		default:
			message = createTextMessage(session);
		}
		return message;
	}

	private Message createObjectMessage(Session session) throws JMSException {
		ObjectMessage message = session.createObjectMessage(new A());
		return message;
	}

	private Message createMapMessage(Session session) throws JMSException {
		MapMessage message = session.createMapMessage();
		message.setBoolean("Boolean", true);
		message.setDouble("XX", 10.0);
		return message;
	}

	private Message createByteMessage(Session session) throws JMSException {
		BytesMessage message = session.createBytesMessage();
		message.writeBytes("ByteArray".getBytes());
		return message;
	}

	private Message createStreamMessage(Session session) throws JMSException {
		StreamMessage message = session.createStreamMessage();
		message.writeByte((byte) 7);
		message.writeBoolean(true);
		return message;
	}

	private TextMessage createTextMessage(Session session) throws JMSException {
		TextMessage message = session.createTextMessage();
		message.setText("Message number["+System.currentTimeMillis()+"]");
		MyLogger.LOGGER.info("Sending message["+message.getText()+"]");
		return message;
	}

	public static int getCount() {
		return count;
	}
}