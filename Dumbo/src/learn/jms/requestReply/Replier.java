package learn.jms.requestReply;

import java.util.concurrent.CountDownLatch;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import learn.jms.BasicJMS;
import learn.jms.MyMessageListener;
import learn.jms.Producer;

public class Replier extends Producer{

	public Replier(String destinationName, long runPeriod,
			boolean isTransactional, CountDownLatch latch)
			throws NamingException {
		super(destinationName, runPeriod, isTransactional, latch);
	}
	
	@Override
	public void run() {
		try {
			final Session session = getSession();
			BasicJMS.getConnection().start();
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MyMessageListener(session){
				@Override
				public void onMessage(Message message) {
					System.out.println("Received Request");
					try {
						Requester.printMessageProperties((TextMessage) message);
						
						Destination replyToDestination = message.getJMSReplyTo();
						MessageProducer replyProducer = session.createProducer(replyToDestination);
						TextMessage replyMsg = session.createTextMessage();
						replyMsg.setText(((TextMessage) message).getText());
						replyMsg.setJMSCorrelationID(message.getJMSMessageID());
						replyProducer.send(replyMsg);
						System.out.println("Sent Reply");
						Requester.printMessageProperties(replyMsg);
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws NamingException {
		new Replier(args[0], 0, false, null).run();
	}
}
