package jetty.server.refactored;

import jetty.server.MyFilter;
import jetty.server.MyServlet;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.thread.BoundedThreadPool;
import org.mortbay.thread.ThreadPool;

public class RefactoredServer {
	private static final int PORT = 8080;
	private Server jettyServer;

	public static void main(String[] args) throws Exception {
		RefactoredServer myServer = new RefactoredServer();
		myServer.configure();
		myServer.start();
		
	}

	private void start() throws Exception {
		jettyServer.start();
	}

	private void configure() {
		jettyServer = new Server();

		Connector connector = getNIOConnector();

		ThreadPool threadPool = getThreadPool();

		Handler ctx = getContext();
		jettyServer.addHandler(ctx);

		jettyServer.addConnector(connector);
		jettyServer.setThreadPool(threadPool);

	}

	public static Context getContext() {
		Context ctx = new Context();
		ctx.setContextPath("/ctx");
		ctx.addServlet(MyServlet.class, "/test");
		ctx.addFilter(MyFilter.class, "/*", Handler.DEFAULT);
		return ctx;
	}

	public static BoundedThreadPool getThreadPool() {
		BoundedThreadPool threadPool = new BoundedThreadPool();
		threadPool.setMinThreads(1);
		threadPool.setMaxThreads(100);
		return threadPool;
	}

	public static SelectChannelConnector getNIOConnector() {
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(PORT);
		return connector;
	}
}
