package learn.nio.netty.http.client;

public enum State {
	CONNECTING,
	SENDING_HEADERS,
	SENDING_DATA,
	SENDING_RECEIVING_DATA,
	RECEIVING_DATA
}
