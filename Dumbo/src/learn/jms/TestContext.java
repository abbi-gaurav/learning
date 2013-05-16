package learn.jms;

import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestContext {
	public static void main(String[] args) throws NamingException {
		Hashtable< String, String> environment = new Hashtable<String, String>();
		environment.put("java.naming.provider.url", "tcp://localhost:61616");
		environment.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		
		System.out.println(new InitialContext(environment));
	}
}
