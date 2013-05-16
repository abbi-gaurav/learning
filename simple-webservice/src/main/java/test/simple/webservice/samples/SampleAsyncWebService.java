package test.simple.webservice.samples;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;

public class SampleAsyncWebService {
	public OMElement testAsynchronity(OMElement name) throws InterruptedException {
		System.out.println("Got name[" + name + "]");
		Thread.sleep(10 * 1000);
		OMElement element = OMAbstractFactory.getSOAP12Factory().createOMElement(new QName("SomeNS"));
		element.setText("Response text");
		return element;
	}
}
