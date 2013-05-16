package handlers;


public class OutgoingCounterHandler extends CounterHandler{

	public static final String OUTGOING_MSG_COUNT_KEY = "outgoingCounterKey";

	protected String getCountKey() {
		return OUTGOING_MSG_COUNT_KEY;
	}

	protected String getHandlerMsg() {
		return "The outgoing message count is:";
	}
}