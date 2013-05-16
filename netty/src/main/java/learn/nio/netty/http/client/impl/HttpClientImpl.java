package learn.nio.netty.http.client.impl;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import learn.nio.netty.http.client.ConnectionInfo;
import learn.nio.netty.http.client.DataTransporter;
import learn.nio.netty.http.client.HttpClient;
import learn.nio.netty.http.client.callbacks.ConnectionEventCallback;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class HttpClientImpl implements HttpClient{
	
	private final ClientBootstrap clientBootstrap;

	//TODO:inject executor creation decision
	public HttpClientImpl(boolean isSSL) {
		Executor bossExecutor = Executors.newCachedThreadPool();
		Executor workerExecutor = Executors.newCachedThreadPool();
		
		clientBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(bossExecutor, workerExecutor));
		clientBootstrap.setPipelineFactory(new HttpClientPipelineFactory(isSSL));
	}
	
	@Override
	public DataTransporter connect(ConnectionInfo connectionInfo, final ConnectionEventCallback callback) {
		InetSocketAddress remoteAddress = new InetSocketAddress(connectionInfo.getRemoteHost(), connectionInfo.getPort());
		InetSocketAddress localAddress = new InetSocketAddress(connectionInfo.getLocalHost(), 0);
		
		ChannelFuture future = clientBootstrap.connect(remoteAddress,localAddress);
		DataTransporter dataTransporter = new DataTransporterImpl(future.getChannel());
		Utils.handleConnectionFuture(callback, future);
		return dataTransporter;
	}
}
