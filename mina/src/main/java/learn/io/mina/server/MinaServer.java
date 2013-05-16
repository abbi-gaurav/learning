package learn.io.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.Charset;

import learn.io.mina.server.metadata.ServerConfiguration;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	private final IoAcceptor acceptor;

	public MinaServer(SocketAddress inetSocketAddress, ServerConfiguration serverConfiguration) throws IOException {
		acceptor = configureAcceptor(inetSocketAddress, serverConfiguration);
	}

	private static IoAcceptor configureAcceptor(SocketAddress inetSocketAddress, ServerConfiguration serverConfiguration)
			throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();
		filterChain.addLast("logging", new LoggingFilter());
		filterChain.addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.defaultCharset())));
		
		acceptor.setHandler(new ServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(serverConfiguration.getReadBufferSize());
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, serverConfiguration.getIdleTime());
		acceptor.bind(inetSocketAddress);
		return acceptor;
	}
	
	public static void main(String[] args) throws IOException {
		InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 25001);
		ServerConfiguration serverConfiguration = new ServerConfiguration(32*1024, 60);
		new MinaServer(inetSocketAddress, serverConfiguration);
	}
}
