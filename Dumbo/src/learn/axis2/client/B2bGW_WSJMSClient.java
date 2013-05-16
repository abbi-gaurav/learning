package learn.axis2.client;

import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;

public class B2bGW_WSJMSClient {
	private static final String ADDRESS = "jms:jndi:dynamicQueues/testSoapOverJms?" +
					"targetService=WS_EDI217_1_WebServiceWithJMS&" +
					"jndi-java.naming.provider.url=tcp://localhost:61616&" +
					"jndi-java.naming.factory.initial=org.apache.activemq.jndi.ActiveMQInitialContextFactory&" +
			"jndi-connectionFactoryNames=connectionFactory";
	private static final String JNDI_HYPHEN = "jndi-";
	private static final String JMS_JNDI_PREFIX = "jms:jndi:";
	private static final String DESTINATION = "destination";
	private static final String REPLY_DESTINATION = "dynamicQueues/testSoapOverJmsReply";

	public static void main(String[] args) throws Exception {
		new B2bGW_WSJMSClient().syncCall();
	}

	public OMElement syncCall()
	throws Exception {
		try {
			SOAPEnvelope soapEnv = Utils.getSoapRequestFromFile
			("/home/gauravabbi/learning/webservices/inputFiles/WS_EDI217_1_WebServiceWithJMS_FullyIntegrated_SoapReq.xml");
			sendSoapOverJMS(soapEnv.toStringWithConsume());
		} catch (XMLStreamException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		return null;
	}

	private void sendSoapOverJMS(String soapRequestString) throws JMSException {
		Hashtable<String, String> envMap = getEPRProperties(ADDRESS);
		Session session = null;
		Connection connection = null;
		try {
			InitialContext ctx = new InitialContext(envMap);
			ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("connectionFactory");
			connection = connectionFactory.createConnection();
			connection.start();
			Destination destination = (Destination) ctx.lookup(envMap.get(DESTINATION));
			Destination replyDestination = (Destination) ctx.lookup(REPLY_DESTINATION);
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			setMessageProperties(envMap,message,replyDestination);
			message.setText(soapRequestString);
			producer.send(destination,message);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			connection.close();
		}
	}

	private void setMessageProperties(Hashtable<String, String> envMap, TextMessage message, Destination replyDestination) throws JMSException {
		message.setJMSReplyTo(replyDestination);
		message.setStringProperty("SOAPJMS_targetService", envMap.get("targetService"));
		message.setStringProperty("SOAPJMS_bindingVersion", "1.0");
		message.setStringProperty("SOAPJMS_contentType", "text/xml");
		message.setStringProperty("SOAPJMS_soapAction", "RealTimeTransaction");
		message.setStringProperty("SOAPJMS_requestURI", ADDRESS.substring(0,ADDRESS.indexOf("?")));
	}

	public static Hashtable<String,String> getEPRProperties(String url) {
		Hashtable<String,String> envMap = new Hashtable<String,String>();
		int propPos = url.indexOf("?");
		if (propPos != -1) {
			StringTokenizer st = new StringTokenizer(url.substring(propPos + 1), "&");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				int sep = token.indexOf("=");
				if (sep != -1) {
					String keyWithjndiHyphen = token.substring(0, sep);
					String key = (keyWithjndiHyphen.startsWith(JNDI_HYPHEN)) ? keyWithjndiHyphen.substring(keyWithjndiHyphen.indexOf(JNDI_HYPHEN)+JNDI_HYPHEN.length())
							:keyWithjndiHyphen;
					String value = token.substring(sep + 1);
					envMap.put(key, value);
				} else {
					// ignore, what else can we do?
				}
			}
		}
		int destinationNameStartPos = url.indexOf(JMS_JNDI_PREFIX);
		String destinationName = url.substring(destinationNameStartPos+JMS_JNDI_PREFIX.length(), propPos);
		envMap.put(DESTINATION, destinationName);
		return envMap;
	}
}
