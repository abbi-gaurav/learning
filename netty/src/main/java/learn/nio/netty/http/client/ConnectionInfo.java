package learn.nio.netty.http.client;

public class ConnectionInfo {
	private final String remoteHost;
	private final int port;
	private final String localHost;

	public ConnectionInfo(String remoteHost, int port, String localHost) {
		this.remoteHost = remoteHost;
		this.port = port;
		this.localHost = localHost;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public int getPort() {
		return port;
	}

	public String getLocalHost() {
		return localHost;
	}
}