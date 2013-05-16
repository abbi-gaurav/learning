package learn.axis2.client;

import javax.xml.namespace.QName;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;

public interface IWSClient {

	public abstract void asyncCall(QName qOperationName, OMElement payload)
			throws AxisFault, InterruptedException;

	public abstract OMElement syncCall(QName qOperationName, OMElement payload)
			throws AxisFault;

	public abstract QName getQualifiedOperationName(String string);

	public abstract ServiceClient getServiceClient();

}