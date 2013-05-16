package learn.nio.netty.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import learn.nio.netty.CommonUtils;
import learn.nio.netty.utils.Helper;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeServer {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeServer.class);
	
	private final ExecutorService workerExecutor = Executors.newCachedThreadPool();
	private final ExecutorService bossExecutor = Executors.newCachedThreadPool();
	private final Configuration configuration;
	private final ServerBootstrap bootstrap;
	
	public TimeServer(InetAddress hostname, int port, Configuration configuration) {
		this.configuration = configuration;
		this.bootstrap = createServerBootStrap(hostname, port);
	}

	public void stop(){
	}
	
	private ServerBootstrap createServerBootStrap(InetAddress hostname, int port) {
		ChannelFactory channelFactory = new NioServerSocketChannelFactory(bossExecutor, workerExecutor, Runtime
				.getRuntime().availableProcessors() * 4);
		ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);
		Helper.configurePipeLine(bootstrap,configuration);
		bootstrap.bind(new InetSocketAddress(hostname,port));
		LOGGER.debug("Started server on host {}, port {}", CommonUtils.getLoggableArgs(hostname,port));
		return bootstrap;
	}

	public static void main(String[] args) throws UnknownHostException {
		new TimeServer(InetAddress.getByName("localhost"), 34000, new Configuration() {
			public long getEventWaitTimeout() {
				return 60;
			}
		});
	}
}
