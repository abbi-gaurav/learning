package test.simple.webservice.samples;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.context.ServiceContext;

//TODO: does not work for now, 
//FIXME:
public class SoapSessionService {
	private static final String VALUE = "VALUE";

	public int add(int value){
		MessageContext msgCtx = MessageContext.getCurrentMessageContext();
		ServiceContext serviceCtx = msgCtx.getServiceContext();
		Object previousValue = serviceCtx.getProperty(VALUE);
		int previousValueNum = 0;
		if(previousValue != null){
			previousValueNum = Integer.parseInt((String)previousValue);
		}
		int currentValueNum = previousValueNum+10;
		serviceCtx.setProperty(VALUE,""+currentValueNum);
		
		return currentValueNum;
	}
}
