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
import learn.jms.Producer;

public class Requester extends Producer{

	private final Destination replyDestination;

	public Requester(String destinationName, long runPeriod,
			boolean isTransactional, CountDownLatch latch,String replyQueue)
			throws NamingException {
		super(destinationName, runPeriod, false, null);
		this.replyDestination = (Destination) BasicJMS.getJndiContext().lookup(replyQueue);
	}
	
	@Override
	public void run() {
		try {
			BasicJMS.getConnection().start();
			Session session = getSession();
			sendMsg(session);
			recieveSync(session);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void recieveSync(Session session) throws JMSException {
		MessageConsumer replyConsumer = session.createConsumer(replyDestination);
		Message replyMsg = replyConsumer.receive();
		System.out.println("Reply Recieved");
		printMessageProperties((TextMessage) replyMsg);
	}

	protected void sendMsg(Session session) throws JMSException {
		final MessageProducer producer = session.createProducer(destination);
		TextMessage requestMessage = (TextMessage) createMessage(session, 0);
		requestMessage.setJMSReplyTo(replyDestination);
		producer.send(requestMessage);
		System.out.println("Sent request");
		printMessageProperties(requestMessage);
	}

	public static void printMessageProperties(TextMessage message)
			throws JMSException {
		System.out.println("\tTime:       " + System.currentTimeMillis() + " ms");
		System.out.println("\tMessage ID: " + message.getJMSMessageID());
		System.out.println("\tCorrel. ID: " + message.getJMSCorrelationID());
		System.out.println("\tReply to:   " + message.getJMSReplyTo());
		System.out.println("\tContents:   " + message.getText());
	}
	
	public static void main(String[] args) throws NamingException {
		new Requester(args[0], 0, false, null, args[1]).run();
	}
}
