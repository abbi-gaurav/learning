package learn.axis2.client;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;

import learn.axis2.client.listener.HttpTransportListener;
import learn.axis2.client.sender.HttpSender;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.OperationClient;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.TransportInDescription;
import org.apache.axis2.description.TransportOutDescription;
import org.apache.axis2.engine.AxisEngine;

public class AsynchronousClient extends WebServiceClient{

	private static final String OUTBOUND = "Outbound";
	private static final String INBOUND = "inbound";
	private HttpTransportListener listener;
	private final TransportInDescription transportIn;
	private final String to;

	public AsynchronousClient(String wsdlUrl, String namespace, String to, int listenerPort) throws Exception {
		super(wsdlUrl, namespace);
		this.to = to;
		listener = new HttpTransportListener(listenerPort);
		transportIn = new TransportInDescription(INBOUND);
		transportIn.setReceiver(listener);
		getConfigurationContext().getAxisConfiguration().addTransportIn(transportIn);
	}
	
	@Override
	protected void configure() throws Exception {
		ServiceClient serviceClient = getServiceClient();
		serviceClient.engageModule("addressing");
		AxisService axisService = serviceClient.getAxisService();
		axisService.addExposedTransport(INBOUND);
		axisService.setEnableAllTransports(true);
	}
	
	@Override
	public void asyncCall(QName qOperationName, OMElement payload) throws AxisFault, InterruptedException {
		ServiceClient serviceClient = getServiceClient();
		MessageContext msgCtx = prepareMsgCtx(qOperationName);
		
		setOpCtx(qOperationName, serviceClient, msgCtx);
		setEnvelope(payload, msgCtx);
		setTransportOut(qOperationName, serviceClient, msgCtx);
		
		AxisEngine.send(msgCtx);
	}

	private void setTransportOut(QName qOperationName, ServiceClient serviceClient, MessageContext msgCtx) {
		TransportOutDescription transportOut = new TransportOutDescription(OUTBOUND + serviceClient.getAxisService().getName() + "_"
				+ qOperationName.getLocalPart());
		transportOut.setSender(new HttpSender());
		msgCtx.setTransportOut(transportOut);
	}

	private void setEnvelope(OMElement payload, MessageContext msgCtx) throws AxisFault {
		SOAPEnvelope soapEnv = Utils.getSoapEnvelope(payload);
		msgCtx.setEnvelope(soapEnv);
	}

	private void setOpCtx(QName qOperationName, ServiceClient serviceClient, MessageContext msgCtx) throws AxisFault {
		OperationClient operationclient = serviceClient.createClient(qOperationName);
		msgCtx.setOperationContext(operationclient.getOperationContext());
	}

	private MessageContext prepareMsgCtx(QName qOperationName) throws AxisFault {
		ServiceClient serviceClient = getServiceClient();
		MessageContext msgCtx = Utils.configureMsgCtx(serviceClient);
		Options options = msgCtx.getOptions();
		options.setAction(qOperationName.getLocalPart());
		// set the location of the web service
		options.setTo(new EndpointReference(to));
		
		// inform Axis2 engine that you need to use a different channel for the response
		options.setUseSeparateListener(true);
		options.setListener(listener);
		options.setTransportIn(transportIn);
		options.setProperty(Constants.Configuration.USE_CUSTOM_LISTENER, true);
		options.setReplyTo(listener.getEPRForService(null, null));
		options.setMessageId("MessageId"+System.currentTimeMillis());
		return msgCtx;
	}
	
	public static void main(String[] args) throws FactoryConfigurationError, Exception {
		AsynchronousClient client = new AsynchronousClient(args[0], args[1],args[3], 25001);
		QName operationName = client.getQualifiedOperationName(args[2]);
		OMElement payload = Utils.createPayLoadFromFile(operationName.getLocalPart(), client.getServiceClient().getAxisService().getName());
//		client.syncCall(operationName, payload);
		client.asyncCall(operationName,payload);
	}
}
