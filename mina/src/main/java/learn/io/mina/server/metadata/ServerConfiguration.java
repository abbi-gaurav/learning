package learn.io.mina.server.metadata;

public class ServerConfiguration {
	private final int readBufferSize;
	private final int idleTime;

	public ServerConfiguration(int readBufferSize, int idleTime) {
		this.readBufferSize = readBufferSize;
		this.idleTime = idleTime;
	}

	public int getReadBufferSize() {
		return readBufferSize;
	}

	public int getIdleTime() {
		return idleTime;
	}
}