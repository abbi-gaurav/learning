package jetty.server;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.thread.BoundedThreadPool;

public class MyServer1 {
	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server jettyServer = new Server();
		
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(PORT);
		BoundedThreadPool threadPool = new BoundedThreadPool();
		threadPool.setMinThreads(1);
		threadPool.setMaxThreads(100);
		
		Context ctx = new Context();
		ctx.setContextPath("/ctx");
		ctx.addServlet(MyServlet.class, "/test");
		ctx.addFilter(MyFilter.class, "/*", Handler.DEFAULT);
		
		jettyServer.addConnector(connector);
		jettyServer.addHandler(ctx);
		jettyServer.setThreadPool(threadPool);
		jettyServer.start();
		jettyServer.join();
	}
}
