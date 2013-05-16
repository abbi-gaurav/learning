package handlers;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler.InvocationResponse;
import org.apache.axis2.handlers.AbstractHandler;

public abstract class CounterHandler extends AbstractHandler{

	public InvocationResponse invoke(MessageContext msgContext)throws AxisFault {
		incrementCount(msgContext);
		return InvocationResponse.CONTINUE;
	}

	protected void incrementCount(MessageContext msgContext) {
		ConfigurationContext context = msgContext.getConfigurationContext();
		Integer count = (Integer) context.getProperty(getCountKey());
		count = Integer.valueOf(count.intValue()+1);
		context.setProperty(getCountKey(), count);
		
		String handlerMsg = getHandlerMsg();
		System.out.println(handlerMsg+count);
	}

	protected abstract String getCountKey();
	
	protected abstract String getHandlerMsg();
	
}
