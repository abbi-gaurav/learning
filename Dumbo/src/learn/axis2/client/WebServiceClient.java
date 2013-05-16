package learn.axis2.client;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;

public class WebServiceClient implements IWSClient {

	public static final String DIRECTORY = "/home/gauravabbi/learning/webservices/inputFiles";
	private final ServiceClient serviceClient;
	protected final String namespace;
	protected ConfigurationContext configCtx;

	public WebServiceClient(String wsdlUrl, String namespace) throws Exception {
		configCtx = getConfigurationContext();
		this.serviceClient = wsdlUrl != null ?new ServiceClient(configCtx,new URL(wsdlUrl),null, null) :
													new ServiceClient(configCtx, null);
		this.namespace = namespace;
		configure();
	}

	protected ConfigurationContext getConfigurationContext() throws AxisFault {
		return ConfigurationContextFactory.
				createConfigurationContextFromFileSystem("/home/gauravabbi/learning/webservices/clientrepo",
						"/home/gauravabbi/learning/webservices/clientrepo/clientconf.xml");
	}
	
	protected void configure() throws AxisFault, Exception {}


	public QName getQualifiedOperationName(String operationName) {
		return new QName(namespace, operationName);
	}

	/* (non-Javadoc)
	 * @see learn.axis2.client.IWSClient#asyncCall(javax.xml.namespace.QName, org.apache.axiom.om.OMElement)
	 */
	@Override
	public void asyncCall(QName qOperationName,OMElement payload) throws AxisFault, InterruptedException{
		java.util.concurrent.CountDownLatch latch = new CountDownLatch(1);
		MyAxisCallback callback = new MyAxisCallback(latch);
		serviceClient.sendReceiveNonBlocking(qOperationName, payload, callback);
		latch.await();
	}

	/* (non-Javadoc)
	 * @see learn.axis2.client.IWSClient#syncCall(javax.xml.namespace.QName, org.apache.axiom.om.OMElement)
	 */
	@Override
	public OMElement syncCall(QName qOperationName, OMElement payload) throws AxisFault {
		OMElement response = serviceClient.sendReceive(qOperationName, payload);
		System.out.println(response);
		return response;
	}

	public ServiceClient getServiceClient() {
		return serviceClient;
	}
	
	public static void main(String[] args) throws Exception, 
	FileNotFoundException, XMLStreamException, FactoryConfigurationError {
		WebServiceClient wsClient = new WebServiceClient(args[0],args[1]);
		sendWithClient(args, wsClient);
		
	}

	protected static void sendWithClient(String[] args,IWSClient wsClient)throws
			XMLStreamException, FactoryConfigurationError, AxisFault,
			InterruptedException, FileNotFoundException {
		//args[2] to end is operations to be performed
		for(int i =2;i<args.length;i++){
			QName qOperationName = wsClient.getQualifiedOperationName(args[i]);
			
			OMElement payload = Utils.createPayLoadFromFile(qOperationName.getLocalPart(),
					wsClient.getServiceClient().getAxisService().getName());
			
			wsClient.syncCall(qOperationName, payload);
			wsClient.asyncCall(qOperationName, payload);
		}
	}
}
