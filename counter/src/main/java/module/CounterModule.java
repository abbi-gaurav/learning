package module;

import handlers.IncomingCounterHandler;
import handlers.OutgoingCounterHandler;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.AxisDescription;
import org.apache.axis2.description.AxisModule;
import org.apache.axis2.modules.Module;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public class CounterModule implements Module{

	public void init(ConfigurationContext configContext, AxisModule module)
			throws AxisFault {
		initCount(IncomingCounterHandler.INCOMING_MSG_COUNT_KEY,configContext);
		initCount(OutgoingCounterHandler.OUTGOING_MSG_COUNT_KEY,configContext);
	}

	private void initCount(String key,
			ConfigurationContext configContext) {
		Integer count = (Integer) configContext.getProperty(key);
		if(count == null){
			configContext.setProperty(key, 0);
		}
	}

	public void engageNotify(AxisDescription axisDescription) throws AxisFault {
		System.out.println("Counter module is engaged");
	}

	public boolean canSupportAssertion(Assertion assertion) {
		return false;
	}

	public void applyPolicy(Policy policy, AxisDescription axisDescription)
			throws AxisFault {
		System.out.println("policy applied");
	}

	public void shutdown(ConfigurationContext configurationContext)
			throws AxisFault {
		System.out.println("Total incoming:"+configurationContext.getProperty(IncomingCounterHandler.INCOMING_MSG_COUNT_KEY));
		System.out.println("Total outgoing:"+configurationContext.getProperty(OutgoingCounterHandler.OUTGOING_MSG_COUNT_KEY));
	}

}
