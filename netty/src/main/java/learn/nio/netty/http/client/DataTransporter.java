package learn.nio.netty.http.client;


import org.jboss.netty.buffer.ChannelBuffer;

import learn.nio.netty.http.client.callbacks.ConnectionEventCallback;
import learn.nio.netty.http.client.callbacks.WriteCallback;

public interface DataTransporter {
	public void writeHeaders(RequestHeaders requestHeaders, WriteCallback callback);
	
	public void close(ConnectionEventCallback callback);

	void writeBody(ChannelBuffer buffer, WriteCallback callback);
}
