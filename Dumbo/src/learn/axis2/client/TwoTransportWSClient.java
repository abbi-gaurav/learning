package learn.axis2.client;

import javax.xml.stream.FactoryConfigurationError;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;

public class TwoTransportWSClient extends WebServiceClient{

	public TwoTransportWSClient(String wsdlUrl, String namespace)
			throws Exception {
		super(wsdlUrl, namespace);
	}

	protected void configure() throws AxisFault {
		Options options = getServiceClient().getOptions();
		getServiceClient().engageModule("addressing");
		options.setUseSeparateListener(true);
		options.setTransportInProtocol(Constants.TRANSPORT_TCP);
	}
	
	public static void main(String[] args) throws FactoryConfigurationError, Exception {
		TwoTransportWSClient client = new TwoTransportWSClient(args[0], args[1]);
		sendWithClient(args, client);
	}
}
