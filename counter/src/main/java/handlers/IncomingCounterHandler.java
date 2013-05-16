package handlers;


public class IncomingCounterHandler extends CounterHandler{

	public static final String INCOMING_MSG_COUNT_KEY = "incomingCounterKey";

	protected String getCountKey() {
		return INCOMING_MSG_COUNT_KEY;
	}

	protected String getHandlerMsg() {
		return "The incoming message count is:";
	}
}
