package learn.axis2.sender;

import javax.xml.stream.FactoryConfigurationError;

import org.apache.axiom.om.OMOutputFormat;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.TransportOutDescription;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.transport.TransportSender;
import org.apache.axis2.transport.TransportUtils;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.util.JavaUtils;

public class TCPTransportSender extends AbstractHandler implements TransportSender{

	private TransportOutDescription transportOut;
	private int soTimeout;
	private int connectionTimeout;


	@Override
	public InvocationResponse invoke(MessageContext msgContext)
			throws AxisFault {
        try {
            OMOutputFormat format = new OMOutputFormat();
            // if (!msgContext.isDoingMTOM())
            msgContext.setDoingMTOM(TransportUtils.doWriteMTOM(msgContext));
            msgContext.setDoingSwA(TransportUtils.doWriteSwA(msgContext));
            msgContext.setDoingREST(TransportUtils.isDoingREST(msgContext));
            format.setSOAP11(msgContext.isSOAP11());
            format.setDoOptimize(msgContext.isDoingMTOM());
            format.setDoingSWA(msgContext.isDoingSwA());
            format.setCharSetEncoding(TransportUtils.getCharSetEncoding(msgContext));

            Object mimeBoundaryProperty = msgContext
                    .getProperty(Constants.Configuration.MIME_BOUNDARY);
            if (mimeBoundaryProperty != null) {
                format.setMimeBoundary((String) mimeBoundaryProperty);
            }

             // set the timeout properties
            Parameter soTimeoutParam = transportOut.getParameter(HTTPConstants.SO_TIMEOUT);
            Parameter connTimeoutParam = transportOut.getParameter(HTTPConstants.CONNECTION_TIMEOUT);

            // set the property values only if they are not set by the user explicitly
            if ((soTimeoutParam != null) &&
                    (msgContext.getProperty(HTTPConstants.SO_TIMEOUT) == null)) {
                msgContext.setProperty(HTTPConstants.SO_TIMEOUT,
                        new Integer((String) soTimeoutParam.getValue()));
            }

            if ((connTimeoutParam != null) &&
                    (msgContext.getProperty(HTTPConstants.CONNECTION_TIMEOUT) == null)) {
                msgContext.setProperty(HTTPConstants.CONNECTION_TIMEOUT, 
                        new Integer((String) connTimeoutParam.getValue()));
            }

            //if a parameter has set been set, we will omit the SOAP action for SOAP 1.2
            if (!msgContext.isSOAP11()) {
                Parameter param = transportOut.getParameter(HTTPConstants.OMIT_SOAP_12_ACTION);
                Object parameterValue = null;
                if (param != null) {
                    parameterValue = param.getValue();
                }

                if (parameterValue != null && JavaUtils.isTrueExplicitly(parameterValue)) {
                    //Check whether user has already overridden this.
                    Object propertyValue = msgContext.getProperty(
                            Constants.Configuration.DISABLE_SOAP_ACTION);

                    if (propertyValue == null || !JavaUtils.isFalseExplicitly(propertyValue)) {
                        msgContext.setProperty(Constants.Configuration.DISABLE_SOAP_ACTION,
                                Boolean.TRUE);
                    }
                }
            }

            // Transport URL can be different from the WSA-To. So processing
            // that now.
            EndpointReference epr = null;
            String transportURL = (String) msgContext
                    .getProperty(Constants.Configuration.TRANSPORT_URL);

            if (transportURL != null) {
                epr = new EndpointReference(transportURL);
            } else if (msgContext.getTo() != null
                    && !msgContext.getTo().hasAnonymousAddress()) {
                epr = msgContext.getTo();
            }
            
            //do a normal TCP Send with an output stream by opening a client socket
        } catch (FactoryConfigurationError e) {
            throw AxisFault.makeFault(e);
        } /*catch (IOException e) {
            throw AxisFault.makeFault(e);
        }*/
        return InvocationResponse.CONTINUE;
    
	}

	@Override
	public void init(ConfigurationContext confContext,
			TransportOutDescription transportOut) throws AxisFault {
			this.transportOut = transportOut;
        

        // Get the timeout values from the configuration
        try {
            Parameter tempSoTimeoutParam = transportOut
                    .getParameter(HTTPConstants.SO_TIMEOUT);
            Parameter tempConnTimeoutParam = transportOut
                    .getParameter(HTTPConstants.CONNECTION_TIMEOUT);

            if (tempSoTimeoutParam != null) {
                soTimeout = Integer.parseInt((String) tempSoTimeoutParam
                        .getValue());
            }

            if (tempConnTimeoutParam != null) {
                connectionTimeout = Integer
                        .parseInt((String) tempConnTimeoutParam.getValue());
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid timeout value format: not a number["+nfe.getLocalizedMessage()+"]");
        }
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void cleanup(MessageContext msgContext) throws AxisFault {
		// TODO Auto-generated method stub
		
	}

}
