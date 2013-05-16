package test.simple.handler;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogHandler extends AbstractHandler implements Handler{
	private static final Log LOG = LogFactory.getLog(LogHandler.class);
	
	public InvocationResponse invoke(MessageContext msgContext)
			throws AxisFault {
		LOG.info(msgContext.getEnvelope().toString());
		return InvocationResponse.CONTINUE;
	}

}
