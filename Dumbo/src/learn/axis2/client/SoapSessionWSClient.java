package learn.axis2.client;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;

public class SoapSessionWSClient extends WebServiceClient{

	public SoapSessionWSClient(String wsdlUrl, String namespace)
			throws Exception {
		super(wsdlUrl, namespace);
	}
	
	@Override
	protected void configure() throws AxisFault {
		getServiceClient().engageModule("addressing");
		getServiceClient().getOptions().setManageSession(true);
	}
	
	public static void main(String[] args) throws FactoryConfigurationError, Exception {
		sendWithClient(args, new SoapSessionWSClient(args[0], args[1]));
	}
	
	@Override
	public OMElement syncCall(QName qOperationName, OMElement payload)
			throws AxisFault {
		super.syncCall(qOperationName, payload);
		return super.syncCall(qOperationName, payload);
	}
	
	@Override
	public void asyncCall(QName qOperationName, OMElement payload)
			throws AxisFault, InterruptedException {
	}

}
