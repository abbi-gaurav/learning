package learn.jms;

import java.util.Enumeration;
import java.util.concurrent.CountDownLatch;

import javax.jms.Connection;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

public abstract class JMSClient {

	public JMSClient(String destinationName, boolean isTransactional,
			CountDownLatch latch) throws NamingException {
		
		this.destination = (Destination) BasicJMS.getJndiContext().lookup(destinationName);
		
		this.isTransactional = isTransactional;
		this.latch = latch;
	}

	protected final Destination destination;
	protected final boolean isTransactional;
	protected final CountDownLatch latch;

	protected void commitTransactional(Session session) throws JMSException {
		if(isTransactional){
			session.commit();
		}
	}

	protected void finishClientTask() {
		latch.countDown();
	}

	protected Session getSession() throws JMSException {
		Connection connection = BasicJMS.getConnection();
		return isTransactional?connection.createSession(true, 0)
								:connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	@SuppressWarnings("rawtypes")
	protected void printJMSDefProperties() throws JMSException {
		System.out.println("Printing JMS Properties");
		ConnectionMetaData metadata = BasicJMS.getConnection().getMetaData();
		System.out.println("Provider data["+metadata.getJMSProviderName()+"--"+metadata.getProviderMajorVersion()+"--"+
				metadata.getProviderMinorVersion()+"--"+metadata.getJMSMajorVersion()+"--"+metadata.getJMSMinorVersion());
		Enumeration propertyNames = metadata.getJMSXPropertyNames();
		while(propertyNames.hasMoreElements()){
			System.out.println(propertyNames.nextElement());
		}
		
	}

}
