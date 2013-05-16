package learn.jms;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

public interface IConsumer {

	public abstract MessageConsumer getConsumer(Session session) throws JMSException;
	public int consumeMessage(MessageConsumer consumer) throws JMSException;
}