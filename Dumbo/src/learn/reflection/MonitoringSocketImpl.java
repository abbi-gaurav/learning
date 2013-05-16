package learn.reflection;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

public class MonitoringSocketImpl extends SocketImpl{
	@Override
	protected void shutdownInput() throws IOException {
		delegator.invoke();
	}

	@Override
	protected void shutdownOutput() throws IOException {
		delegator.invoke();
	}

	@Override
	protected FileDescriptor getFileDescriptor() {
		return delegator.invoke();
	}

	@Override
	protected InetAddress getInetAddress() {
		return delegator.invoke();
	}

	@Override
	protected int getPort() {
		Integer result = delegator.invoke();
		return result;
	}

	@Override
	protected boolean supportsUrgentData() {
		Boolean result = delegator.invoke();
		return result;
	}

	@Override
	protected int getLocalPort() {
		return super.getLocalPort();
	}

	@Override
	public String toString() {
		return delegator.invoke();
	}

	@Override
	protected void setPerformancePreferences(int connectionTime, int latency,
			int bandwidth) {
		delegator.invoke(connectionTime,latency,bandwidth);
	}

	private final Delegator delegator;
	
	public MonitoringSocketImpl() {
		this.delegator = new Delegator(this, "java.net.SocksSocketImpl", 
				SocketImpl.class);
	}
	
	
	@Override
	public void setOption(int optID, Object value) throws SocketException {
		delegator.invoke(optID,value);
	}

	@Override
	public Object getOption(int optID) throws SocketException {
		return delegator.invoke(optID);
	}

	@Override
	protected void create(boolean stream) throws IOException {
		delegator.invoke(stream);
	}

	@Override
	protected void connect(String host, int port) throws IOException {
		delegator.invoke(host,port);		
	}

	@Override
	protected void connect(InetAddress address, int port) throws IOException {
		delegator.delegateTo("connect", InetAddress.class,int.class)
		.invoke(address,port);
	}

	@Override
	protected void connect(SocketAddress address, int timeout)
			throws IOException {
		delegator.invoke(address,timeout);
	}

	@Override
	protected void bind(InetAddress host, int port) throws IOException {
		delegator.invoke(host,port);
	}

	@Override
	protected void listen(int backlog) throws IOException {
		delegator.invoke(backlog);
	}

	@Override
	protected void accept(SocketImpl s) throws IOException {
		delegator.invoke(s);		
	}

	@Override
	protected InputStream getInputStream() throws IOException {
		InputStream real = delegator.invoke();
	    return new SocketMonitoringInputStream(getSocket(), real);
	}

	@Override
	protected OutputStream getOutputStream() throws IOException {
		OutputStream real = delegator.invoke();
	    return new SocketMonitoringOutputStream(getSocket(), real);
	}

	@Override
	protected int available() throws IOException {
		Integer result = delegator.invoke();
	    return result;
	}

	@Override
	protected void close() throws IOException {
		delegator.invoke();
	}

	@Override
	protected void sendUrgentData(int data) throws IOException {
		delegator.invoke();
	}

	public Socket getSocket() throws IOException{
		try{
			Field socket = SocketImpl.class.getDeclaredField("socket");
			socket.setAccessible(true);
			return (Socket) socket.get(this);
		} catch (Exception e) {
			throw new IOException("Could not discover real socket");
		}
	}
}
