package learn.jms;

import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.NamingException;

public class SynchronousConsumer extends JMSClient implements Runnable,IConsumer {
	protected static final String MESSAGE_SELECTOR = 
				"JMSType = '"+Producer.JMS_TYPE+"' AND (NumberType = 'ODD' OR NumberType='EVEN')";
	private static int count;

	public SynchronousConsumer(String destinationName,
						boolean isTransactional, CountDownLatch latch) throws NamingException {
		super(destinationName, isTransactional, latch);
	}

	@Override
	public void run() {
		try {
			//printJMSDefProperties();
			Session session = getSession();
			final MessageConsumer consumer = getConsumer(session);
			BasicJMS.getConnection().start();
			consumeMessage(consumer);
			//browse queue
			//browse(session);
			commitTransactional(session);
		}catch (JMSException e) {
			MyLogger.LOGGER.error("Message Recieve failed", e);
			Thread.currentThread().interrupt();
		} finally{
			finishClientTask();
		}
	}

	public void otherJMSRecieveCapabilities(Session session) throws JMSException {
		QueueBrowser browser = session.createBrowser((Queue) destination, MESSAGE_SELECTOR);
		Enumeration enumeration = browser.getEnumeration();
		int count = 0;
		while(enumeration.hasMoreElements()){
			System.out.println("Browsing queue message number["+(++count)+"]");
			enumeration.nextElement();
		}
		
		//queue requester
		//QueueRequestor requester = new QueueRequestor((QueueSession)session, (Queue)destination);
		//JMS request reply scenario
		//requester.request(message);
	}

	public int consumeMessage(MessageConsumer consumer)
			throws JMSException {
		Message message;
		while(true){
			message = consumer.receive(1000);
			if(message == null){
				break;
			}
			count++;
			workWithMessage(message);
			//message.acknowledge();
		}
		MyLogger.LOGGER.info("["+count+"] messages recieved");
		return count;
	}

	@SuppressWarnings("rawtypes")
	public static final void workWithMessage(Message message) throws JMSException {
		System.out.println("NumberType of Recieved Message is["+message.getStringProperty("NumberType")+"]");
		if(message instanceof TextMessage){
			MyLogger.LOGGER.info("Recieved text Message["+((TextMessage)message).getText()+"]");
		}else if(message instanceof StreamMessage){
			StreamMessage sMsg = (StreamMessage)message;
			MyLogger.LOGGER.info("Recieved Stream Message["+sMsg.readByte()+"--"+sMsg.readBoolean()+"]");
		}else if(message instanceof BytesMessage){
			BytesMessage bytesMessage = (BytesMessage)message;
			byte[] read = new byte[(int) bytesMessage.getBodyLength()];
			bytesMessage.readBytes(read);
			MyLogger.LOGGER.info("Recieved bytes["+new String(read)+"]");
		}else if(message instanceof MapMessage){
			MapMessage mapMessage = (MapMessage) message;
			Enumeration propertyNames = mapMessage.getMapNames();
			StringBuilder sb = new StringBuilder();
			while(propertyNames.hasMoreElements()){
				String nextElement = (String) propertyNames.nextElement();
				sb.append(nextElement+"--"+mapMessage.getObject(nextElement));
			}
			MyLogger.LOGGER.info("Recieved map message["+sb.toString()+"]");
		}else if(message instanceof ObjectMessage){
			ObjectMessage objMsg = (ObjectMessage) message;
			MyLogger.LOGGER.info("Recieved object message["+objMsg.getObject()+"]");
		}else{
			MyLogger.LOGGER.info("Recieved Message["+message.toString()+"]");
		}
		message.clearBody();
	}

	public MessageConsumer getConsumer(Session session) throws JMSException {
		MessageConsumer consumer = session.createConsumer(destination,MESSAGE_SELECTOR);
		return consumer;
	}

	public static int getCount() {
		return count;
	}
}
