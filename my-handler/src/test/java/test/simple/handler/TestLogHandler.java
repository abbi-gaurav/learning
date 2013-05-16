package test.simple.handler;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler.InvocationResponse;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class TestLogHandler {
	private LogHandler handler;
	private MessageContext mock;

	@Before
	public void setup(){
		handler = new LogHandler();
		mock = EasyMock.createMock(MessageContext.class);
		EasyMock.expect(mock.getEnvelope()).andReturn(createSoapEnvelope());
		EasyMock.replay(mock);
	}
	

	@Test
	public void testHandle() throws AxisFault{
		org.junit.Assert.assertEquals(handler.invoke(mock),InvocationResponse.CONTINUE);
		EasyMock.verify(mock);
	}
	
	private static SOAPEnvelope createSoapEnvelope() {
		SOAPFactory fac = OMAbstractFactory.getSOAP11Factory();
		SOAPEnvelope envelope = fac.getDefaultEnvelope();
		OMNamespace omNs = fac.createOMNamespace("http://ws.apache.org/axis2", "ns1");
		OMElement method = fac.createOMElement("echo", omNs);
		OMElement value = fac.createOMElement("echo", omNs);
		value.setText("Hello");
		method.addChild(value);
		envelope.getBody().addChild(method);
		return envelope;
	}
}
