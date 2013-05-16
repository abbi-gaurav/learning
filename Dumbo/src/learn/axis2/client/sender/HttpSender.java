package learn.axis2.client.sender;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.description.TransportOutDescription;
import org.apache.axis2.transport.TransportSender;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HttpSender implements TransportSender  {
	
	private final HttpClient client;

	public HttpSender() {
		HttpClientParams params = new HttpClientParams();
		client = new HttpClient(params);
	}
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(HandlerDescription handlerDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InvocationResponse invoke(MessageContext msgCtx) throws AxisFault {
		ByteArrayInputStream is = null;
        try {
        	EndpointReference epr = msgCtx.getTo();
            PostMethod post = new PostMethod(epr.getAddress());
        	configurePost(msgCtx,post);
            
            is = new ByteArrayInputStream(msgCtx.getEnvelope()
                    .toStringWithConsume().getBytes());
            post.setRequestEntity(new InputStreamRequestEntity(is));
            int respCode = client.executeMethod(post);
            System.out.println(respCode);
        } catch (Exception e1) {
        	e1.printStackTrace();
            throw new AxisFault("Input envelope is not well built");
        }
        return InvocationResponse.CONTINUE;
	}

	@Override
	public void flowComplete(MessageContext msgContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HandlerDescription getHandlerDesc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parameter getParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanup(MessageContext msgContext) throws AxisFault {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(ConfigurationContext confContext, TransportOutDescription transportOut) throws AxisFault {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	private PostMethod configurePost(MessageContext msgCtx,PostMethod post) {
        Map<String, String> headers = new HashMap<String, String>();
        String soapAction = msgCtx.getSoapAction();
        if (msgCtx.isSOAP11()) {
            headers.put("SOAPAction", msgCtx.getSoapAction());
            headers.put("Content-Type", "text/xml;charset=UTF-8");
        } else {
            String type = "application/soap+xml;charset=UTF-8;action=\""
                    + ((soapAction == null || soapAction.length() == 0) ? ""
                            : soapAction) + "\"";
            headers.put("Content-Type", type);
        }
        
        for(Map.Entry<String, String> header:headers.entrySet()){
        	post.addRequestHeader(header.getKey(), header.getValue());
        }
        return post;
    }
	
}
