package learn.stax;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.OMText;
import org.apache.axiom.om.impl.OMNavigator;
import org.apache.axiom.om.impl.builder.StAXBuilder;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.apache.axiom.soap.SOAPEnvelope;
import org.jaxen.JaxenException;

public class LearnAxiom {
	private static int position;
	public static void main(String[] args) throws Exception {
		//read(args);
		OMElement node = write();
		
//		serialize(node);
//		navigate(node);
//		xpathNavigation();
		workWithSoap(node);
	}

	private static void workWithSoap(OMElement node) {
		org.apache.axiom.soap.SOAPFactory soapFactory = OMAbstractFactory.getSOAP12Factory();
		SOAPEnvelope env = soapFactory.getDefaultEnvelope();
		env.getBody().addChild(node);
		System.out.println(env);
	}

	private static void xpathNavigation() throws JaxenException, XMLStreamException {
		String xmlStream = "<ns:book xmlns:ns=\"axis2\" type=\"web-services\"><ns:name></ns:name><ns:isbn>56789</ns:isbn></ns:book>";
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(xmlStream.getBytes());
		StAXBuilder builder = new StAXOMBuilder(byteArrayInputStream);
		OMElement root = builder.getDocumentElement();
		System.out.println("xpath");
		AXIOMXPath xpath = new AXIOMXPath("/ns:book/ns:isbn[1]");
		xpath.addNamespace("ns", "axis2");
		OMElement result = (OMElement) xpath.selectSingleNode(root);
		System.out.println(result.getText());
	}

	private static void navigate(OMElement element) {
		System.out.println("Navigating---------");
		OMNavigator navigator = new OMNavigator(element);
		while(navigator.isNavigable()){
			OMNode node = navigator.next();
			System.out.println(node);
		}
	}

	private static void serialize(OMElement element) throws XMLStreamException {
		element.serializeAndConsume(System.out);
		element.serialize(System.out);
	}

	private static OMElement write() {
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace namespace = factory.createOMNamespace("axis2", "ns");
		OMElement root = factory.createOMElement("books", namespace);
		OMAttribute rootAttribute1 = factory.createOMAttribute("type", null, "learnAxiom");
		root.addAttribute(rootAttribute1);
		
		OMElement child = factory.createOMElement("isbn", namespace);
		child.setText("1234567");
		root.addChild(child);
		return root;
	}

	protected static void read(String[] args) throws XMLStreamException,
								FactoryConfigurationError, FileNotFoundException {
		XMLStreamReader reader = XMLInputFactory.newInstance()
								.createXMLStreamReader(new FileInputStream(args[0]));
		StAXOMBuilder builder = new StAXOMBuilder(reader);
		
		OMElement root = builder.getDocumentElement();
		
		print(root);
	}

	private static void print(OMElement element) {
		printOnConsole(element.getLocalName());
		
		position++;
		Iterator<?> iterator = element.getChildren();
		while(iterator.hasNext()){
			Object next = iterator.next();
			if(next instanceof OMElement){
				print((OMElement) next);
			}else if(next instanceof OMText){
				printOnConsole(((OMText)next).getText());
			}
		}
		
		//printAttribute
		printAttribute(element.getAllAttributes());
		//printText
		position--;
	}

	private static void printAttribute(Iterator<?> allAttributes) {
		while(allAttributes.hasNext()){
			OMAttribute attribute = (OMAttribute) allAttributes.next();
			String attributeString = attribute.getAttributeType().trim()+"="+attribute.getAttributeValue().trim();
			printOnConsole(attributeString);
		}
	}

	protected static void printOnConsole(String data) {
		System.out.println(MyStax.printData(data, position));
	}
}
