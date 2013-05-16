package learn.nio.netty.http.client;

import learn.nio.netty.http.client.callbacks.ConnectionEventCallback;

public interface HttpClient {
	public DataTransporter connect(ConnectionInfo connectionInfo, ConnectionEventCallback callback);
	
}
