package learn.axis2.client;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;

public class InOnlyWebServiceClient extends WebServiceClient{

	public InOnlyWebServiceClient(String wsdlUrl, String namespace)
	throws Exception {
		super(wsdlUrl, namespace);
	}
	
	@Override
	public OMElement syncCall(QName qOperationName, OMElement payload)
			throws AxisFault {
		getServiceClient().fireAndForget(qOperationName, payload);
		//getServiceClient().sendRobust(qOperationName, payload);
		return null;
	}
	
	@Override
	public void asyncCall(QName qOperationName, OMElement payload)
			throws AxisFault, InterruptedException {
		//TODO: nothing
	}
	
	public static void main(String[] args) throws 
	FactoryConfigurationError, Exception {
		sendWithClient(args, new InOnlyWebServiceClient(args[0], args[1]));
	}
}
