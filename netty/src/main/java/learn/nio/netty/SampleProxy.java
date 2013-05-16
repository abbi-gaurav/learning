package learn.nio.netty;

import org.littleshoot.proxy.DefaultHttpProxyServer;
import org.littleshoot.proxy.HttpProxyServer;

public class SampleProxy {
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		final HttpProxyServer proxy = new DefaultHttpProxyServer(port);
		if(args.length > 2)
		proxy.addProxyAuthenticationHandler(new ProxyAuthorizationHandlerImpl(args[1], args[2]));
		proxy.start();
		System.out.println("proxy started for port ["+port+"]");
	}
}
