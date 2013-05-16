package learn.axis2.client;

import java.util.HashMap;
import java.util.Map;

import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.xml.stream.FactoryConfigurationError;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNode;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.ParameterInclude;
import org.apache.axis2.transport.jms.JMSConstants;

public class JMSClient extends WebServiceClient{
	private static final String DEFAULT_TRANSPORT_PARAMETER_NAME = "default";
	private static final String NAMESPACE = "http://samples.webservice.simple.test";

	public JMSClient(String wsdlUrl, String namespace) throws Exception {
		super(wsdlUrl, namespace);
	}
	
	public static void main(String[] args) 
					throws FactoryConfigurationError, Exception {
		JMSClient client = new JMSClient(args[0], args[1]);
		sendWithClient(args, client);
	}
	
	@Override
	protected void configure() throws Exception {
		Options options = new Options();
		options.setAction("urn:sayHiInOut");
		options.setUseSeparateListener(true);
		//options.setProperty(Options.CUSTOM_REPLYTO_ADDRESS, Options.CUSTOM_REPLYTO_ADDRESS_TRUE);
		options.setTo(new EndpointReference("jms:/SampleService?java.naming.provider.url=tcp://localhost:61616" +
				"&java.naming.factory.initial=org.apache.activemq.jndi.ActiveMQInitialContextFactory" +
				"&transport.jms.ConnectionFactoryJNDIName=QueueConnectionFactory"));
		options.setReplyTo(new EndpointReference("jms:/JMSClientReply?java.naming.provider.url=tcp://localhost:61616" +
				"&java.naming.factory.initial=org.apache.activemq.jndi.ActiveMQInitialContextFactory" +
				"&transport.jms.ConnectionFactoryJNDIName=QueueConnectionFactory"));
		
//		Map<String, String> params = getAxisTransportConfiguration(false);
//		TransportInDescription transportIn = new TransportInDescription("jms");
//		try {
//			addParameterToTransport(params,transportIn);
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		JMSListener receiver = new JMSListener();
//		//transportIn.setReceiver(receiver);
//		options.setTransportIn(transportIn);
//		receiver.init(configCtx, transportIn);
//		transportIn.setReceiver(receiver);
//		configCtx.getAxisConfiguration().addTransportIn(transportIn);
		getServiceClient().getAxisService().addParameter("transport.jms.Destination", "JMSClientReply");
		//getServiceClient().getAxisService().addParameter("transport.jms.ReplyDestinationType","queue");
		getServiceClient().getServiceContext().setProperty(JMSConstants.JMS_REPLY_TO, "dynamicQueues/JMSClientReply");
		getServiceClient().setOptions(options);
		super.configure();
	}
	
	private Map<String,String> getAxisTransportConfiguration(boolean authenticationEnabled) {

        Map<String,String> properties = new HashMap<String,String>();
        properties.put(JMSConstants.PARAM_CONFAC_JNDI_NAME, "QueueConnectionFactory");
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

        if(authenticationEnabled) {
            properties.put(JMSConstants.PARAM_JMS_USERNAME, "username");
            properties.put(JMSConstants.PARAM_JMS_PASSWORD, "password");
        }

        return properties;
    }
	
	 private void addParameterToTransport(Map<String,String> jmsTransportProps,
	            ParameterInclude transportInOut) throws ConfigurationException {

	        try {
	            Parameter parameter = new Parameter();
	            parameter.setName(DEFAULT_TRANSPORT_PARAMETER_NAME);
	            parameter.setValue(buildParameterOMElementFromMap(DEFAULT_TRANSPORT_PARAMETER_NAME, jmsTransportProps, false));
	            transportInOut.addParameter(parameter);
	        } catch (AxisFault e) {
	            throw new ConfigurationException(e.getMessage());
	        }

	    }

	 
	 private static OMElement buildParameterOMElementFromMap(String rootElementName, Map<String,String> properties, boolean rootIsLocked) {

	        OMFactory omFactory = OMAbstractFactory.getOMFactory();

	        //creating root <parameter>
	        OMElement root = omFactory.createOMElement(new javax.xml.namespace.QName("parameter"));
	        OMAttribute attribute = omFactory.createOMAttribute("name", null, rootElementName);
	        root.addAttribute(attribute);
	        attribute = omFactory.createOMAttribute("locked", null, String.valueOf(rootIsLocked));
	        root.addAttribute(attribute);

	        //creating child <parameter> elements
	        for(String key : properties.keySet())
	        {   
	            OMElement child = omFactory.createOMElement(new javax.xml.namespace.QName("parameter"));
	            attribute = omFactory.createOMAttribute("name", null, key);
	            child.addAttribute(attribute);
	            attribute = omFactory.createOMAttribute("locked", null, Boolean.FALSE.toString());
	            child.addAttribute(attribute);

	            OMNode text = omFactory.createOMText(properties.get(key));
	            child.addChild(text);
	            root.addChild(child);
	        }
	        return root;
	    }
}
