package learn.axis2.client;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.OperationClient;

public class WebServiceOperationClient extends WebServiceClient{

	public WebServiceOperationClient(String wsdlUrl, String namespace)
			throws Exception {
		super(wsdlUrl, namespace);
	}
	
	@Override
	public OMElement syncCall(QName qOperationName, OMElement payload)
			throws AxisFault {
		org.apache.axis2.context.MessageContext ctx = Utils.configureMsgCtx(getServiceClient());
		OperationClient opClient = getServiceClient().createClient(qOperationName);
		ctx.getOptions().setAction(qOperationName.getLocalPart());
		opClient.addMessageContext(ctx);
		
		SOAPEnvelope soapEnvelope = Utils.getSoapEnvelope(payload);
		ctx.setEnvelope(soapEnvelope);
		
		opClient.execute(true);
		org.apache.axis2.context.MessageContext inCtx = opClient.getMessageContext("In");
		
		return inCtx.getEnvelope().getFirstElement();
	}

	public static void main(String[] args) throws FactoryConfigurationError, Exception {
		WebServiceOperationClient wsClient = new WebServiceOperationClient(args[0], args[1]);
		sendWithClient(args, wsClient);
	}

}
