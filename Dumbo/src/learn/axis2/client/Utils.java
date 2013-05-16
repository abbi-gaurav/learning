package learn.axis2.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class Utils {

	public static OMElement createPayLoad(String operationName,String namespace) {
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace omNamespace = factory.createOMNamespace(namespace, "ns");
	
		OMElement rootElement = factory.createOMElement(operationName, omNamespace);
		OMElement child = factory.createOMElement("input", omNamespace);
		child.setText("Sending request");
		rootElement.addChild(child);
		System.out.println(rootElement);
		return rootElement;
	}

	public static OMElement createPayLoadFromFile(String operationName,String serviceName) throws 
				FileNotFoundException, XMLStreamException, FactoryConfigurationError{
		String fileName = (WebServiceClient.DIRECTORY.endsWith("/")?WebServiceClient.DIRECTORY:WebServiceClient.DIRECTORY+"/")+
							serviceName
							+"_"+operationName;
		XMLStreamReader reader = XMLInputFactory.newInstance()
							.createXMLStreamReader(new FileInputStream(fileName));
		
		StAXOMBuilder builder = new StAXOMBuilder(reader);
	
		OMElement root = builder.getDocumentElement();
		//TODO:validation against input schema can be done here
		return root;
	}
	
	public static SOAPEnvelope getSoapRequestFromFile(String fileName) throws FileNotFoundException, XMLStreamException, FactoryConfigurationError{
		SOAPFactory soapFactory = OMAbstractFactory.getSOAP12Factory();
        SOAPEnvelope soapReequest = soapFactory.getDefaultEnvelope();
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(fileName));
		StAXOMBuilder builder = new StAXOMBuilder(reader);
		OMElement returnBody = builder.getDocumentElement();
        soapReequest.getBody().addChild( returnBody );
        return soapReequest;
	}

	public static org.apache.axis2.context.MessageContext configureMsgCtx(ServiceClient servcieClient) {
		org.apache.axis2.context.MessageContext ctx = new org.apache.axis2.context.MessageContext();
		Options options = ctx.getOptions();
		options.setTo(servcieClient.getTargetEPR());
		return ctx;
	}

	public static SOAPEnvelope getSoapEnvelope(OMElement payload) {
		SOAPFactory factory = OMAbstractFactory.getSOAP11Factory();
		SOAPEnvelope soapEnvelope = factory.getDefaultEnvelope();
		soapEnvelope.getBody().addChild(payload);
		return soapEnvelope;
	}

}
