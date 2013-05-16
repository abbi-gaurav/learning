package com.test.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.handler.HandlerWrapper;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.security.Constraint;
import org.mortbay.jetty.security.ConstraintMapping;
import org.mortbay.jetty.security.SecurityHandler;
import org.mortbay.jetty.security.SslSocketConnector;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;
import org.xml.sax.SAXException;

import com.test.servlet.CRLServlet;
import com.test.servlet.ChunkTest;
import com.test.servlet.SpitServlet;
import com.test.servlet.TestHttpSenderServlet;
import com.test.servlet.TestJettyHttpSenderServlet;
import com.test.servlet.TestServlet1;
import com.test.servlet.TestServlet2;
/**
 * will use 8080 as the port to run
 * @author gabbi
 *
 */
public class MyServer {
	private static final String SEND_CHUNK = "/sendChunk";
	private static final String CONTEXT_PATH = "/testHttp";
	private static final String PASSWORD = "password";
	private static final String KEY_STORE_FILE = "/home/gaurav_abbi/official/meg/issues/16052/serverks.jks";
//	private static final String KEY_STORE_FILE = "/home/gaurav_abbi/official/meg/httpclient/ssl/data/server/httpServer.jks";
	
	private final String ip = InetAddress.getLocalHost().getHostAddress();

	private Server server;
	private final Context context;
	private final JettyServerParameters jettyServerParams;
	
	public Context getContext() {
		return context;
	}

	public MyServer(JettyServerParameters jettyServerParams) throws FileNotFoundException, SAXException, IOException, Exception{
		this.jettyServerParams = jettyServerParams;
		this.server = initConfig();
		
		this.context = addServlets(jettyServerParams.getServletMap());
		addConstraint();
	}
	
	private void addConstraint() {
		Constraint constraint = new Constraint();
		constraint.setAuthenticate(true);
		
		ConstraintMapping cm = new ConstraintMapping();
		cm.setPathSpec("/*");
		cm.setMethod("OPTIONS");
		cm.setConstraint(constraint);
		
		if(context.getSecurityHandler() == null){
			context.setSecurityHandler(new SecurityHandler());
		}
		context.getSecurityHandler().setConstraintMappings(
				new ConstraintMapping[] { cm });
	}

	private Server initConfig() throws FileNotFoundException, SAXException, IOException, Exception{
		setLogging(true);
		server = new Server();
		addConnector();
		setupSSL();
		addThreadPool();
		return server;
	}

	private void addThreadPool() {
		QueuedThreadPool tp = new QueuedThreadPool();
		tp.setMinThreads(1);
		tp.setMaxThreads(250);
		server.setThreadPool(tp);
	}

	private void addConnector() throws FileNotFoundException, SAXException, IOException, Exception {
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setHost(ip);
		connector.setPort(jettyServerParams.getPort());
		System.out.println(ip);
		System.out.println(jettyServerParams.getPort());
		
		connector.setAcceptors(3);
		System.out.println("connector configured");
		
		server.addConnector(connector);
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Class<? extends HttpServlet>> servlets = configureServlets();
		int port = Integer.parseInt(args[0]);
		int sslPort = Integer.parseInt(args[1]);
		String ksFile = args.length > 2 ? args[2]: null;
		
		boolean clientAuth = args.length > 3 ? Boolean.getBoolean(args[3]):false;
		
		final MyServer myServer = new MyServer(new JettyServerParameters(port,
				sslPort, CONTEXT_PATH, servlets, ksFile, clientAuth));
		myServer.start();
	}

	private static Map<String, Class<? extends HttpServlet>> configureServlets() {
		Map<String, Class<? extends HttpServlet>> servlets = new HashMap<String, Class<? extends HttpServlet>>();
		servlets.put("/testHttpOutbound",TestJettyHttpSenderServlet.class);
		servlets.put(SEND_CHUNK, ChunkTest.class);
		servlets.put("/testRequest", TestHttpSenderServlet.class);
		servlets.put("/crlIntTest.crl", CRLServlet.class);
		return servlets;
	}
	
	public void start() {
		System.out.println("starting server: "+Thread.currentThread().getName());
		try {
			System.out.println("Server is:["+server+"]");
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("error while starting server\n\n");
			e.printStackTrace();
		}
	}
	
	private void setLogging(boolean setLog){
		if(setLog){
			System.setProperty("org.mortbay.log.class","com.test.util.log.MyLogger");
			//setting debug as true
			System.setProperty("DEBUG","true");
		}
	}
	
	private Context addServlets(Map<String, Class<? extends HttpServlet>> servletMap){
		Context ctx = new Context();
		ctx.setContextPath(jettyServerParams.getContextPath());
		ServletHandler servletHandler = new ServletHandler();
		
		for (Map.Entry<String, Class<? extends HttpServlet>> entry : servletMap.entrySet()) {
			servletHandler.addServletWithMapping(entry.getValue(),entry.getKey());
		}
		
		ctx.setServletHandler(servletHandler);
		server.addHandler(ctx);
		return ctx;
	}
	
	private void stop() {
		// TODO Auto-generated method stub
		System.out.println("stopping server: "+Thread.currentThread().getName());
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("error while starting server\n\n");
			e.printStackTrace();
		}
	}

	private void configureServer() throws FileNotFoundException, SAXException, IOException,Exception{
		//XmlConfiguration configuration = new XmlConfiguration(new FileInputStream("E:\\gaurav\\srcCode\\non-official\\jetty6.1\\jetty-6.1.4\\jetty-6.1.4\\etc\\jetty.xml"));
		//setupSSL();
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setTempDirectory(new File("/home/gauravabbi/official/issues/helloworld_warIssue/tmp"));
		//webAppContext.setContextPath("/helloworld");
		//webAppContext.setWar("E:\\gaurav\\java\\J2EE\\jetty\\wars\\TestWeb1.war");
		webAppContext.setWar("/home/gauravabbi/official/issues/helloworld_warIssue/helloworld.war");
		//webAppContext.setWar("E:\\gaurav\\official\\xfire\\xfire-1.2.6_dist\\examples\\book\\target\\xfire-book-1.2.6.war");
		webAppContext.setExtractWAR(true);
		
		server.addHandler(webAppContext);
		
		//adding second web application
//		WebAppContext webContext2 = new WebAppContext();
//		webContext2.setTempDirectory((new File("E:\\gaurav\\java\\J2EE\\jetty\\wars\\tmp7")));
//		webContext2.setContextPath("/chunkwar");
//		webContext2.setWar("E:\\gaurav\\java\\J2EE\\jetty\\wars\\TestChunk.war");
//		webContext2.setExtractWAR(true);
//		server.addHandler(webContext2);
		
		//adding second web application
//		WebAppContext webContext3 = new WebAppContext();
//		webContext3.setTempDirectory((new File("E:\\gaurav\\java\\J2EE\\jetty\\wars\\tmp10")));
//		webContext3.setContextPath("/struts2tutorial");
//		webContext3.setWar("E:\\gaurav\\official\\devlopment\\ebics\\phase-2\\struts2\\struts2tutorial.war");
//		webContext3.setExtractWAR(true);
//		server.addHandler(webContext3);
		
		server.setStopAtShutdown(true);
		//adding servlet
	//	addServlet(server);
		//configuration.configure(server);
		//configureJNDINamingService();
		
		System.out.println(ip);
	}
	
	private void setupSSL() {
		if(jettyServerParams.getSslPort() == -1 ){
			return;
		}
		// TODO Auto-generated method stub
		SslSocketConnector sslConnector = new SslSocketConnector();
		sslConnector.setKeystore(getKSFile());
		//sslConnector.setKeystoreType("PKCS12");
		sslConnector.setKeyPassword(PASSWORD);
		//sslConnector.setTruststore("E:\\gaurav\\official\\29_10_SB_BAKER\\keystore\\jettyKS");
		//sslConnector.setTruststoreType("PKCS12");
		//sslConnector.setTrustPassword("password");
		sslConnector.setPassword(PASSWORD);
		sslConnector.setPort(jettyServerParams.getSslPort());
		sslConnector.setHost(ip);
		sslConnector.setNeedClientAuth(jettyServerParams.isClientAuth());
		server.addConnector(sslConnector);
	}

	private String getKSFile() {
		String ksFile = jettyServerParams.getKsFile();
		return ksFile != null ? ksFile : KEY_STORE_FILE;
	}

	/**
	 * description:	configures JNDI naming part using jetty plus jar
	 */
	private void configureJNDINamingService() {
		// TODO Auto-generated method stub
		String icf = "org.mortbay.naming.InitialContextFactory";
	    System.setProperty("java.naming.factory.initial", icf);
	}

	private static void add_perfDartBoardWar() throws Exception{
		Server standaloneServer = new Server();
		Connector standaloneListener = new SocketConnector();
        standaloneListener.setPort(Integer.parseInt("9090"));
        QueuedThreadPool btp = new QueuedThreadPool();
        btp.setMinThreads(1);
        btp.setMaxThreads(50);
        btp.setMaxIdleTimeMs(300 * 1000);
        standaloneServer.setThreadPool(btp);
        standaloneServer.addConnector(standaloneListener);
        /*WebAppContext context = new WebAppContext("E:\\gaurav\\official\\devlopment\\jett6.xupgrade\\war","/");//{
*/        WebAppContext context = new WebAppContext("E:\\gaurav\\java\\J2EE\\jetty\\wars\\TestWeb2.war","/"){
            public void doStart() throws Exception {
                Thread thread = Thread.currentThread();
                ClassLoader lastContextLoader = thread.getContextClassLoader();
                try {
                	//temp need to see high hope so it should serve the purpose
                    thread.setContextClassLoader(getClassLoader());
                    super.doStart();
                } finally {
                    thread.setContextClassLoader(lastContextLoader);
                }
            }
        };
        /*context.setTempDirectory((new File("E:\\gaurav\\java\\J2EE\\jetty\\wars\\pertfDartBoard")));
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //if (classLoader == null)
            //classLoader = getClassLoader();
        
        context.setClassLoader(classLoader);*/
        context.setVirtualHosts(new String[]{"localhost"});
        standaloneServer.addHandler(context);
        standaloneServer.start();
	}
	
	private static void testProxy() throws Exception{
		Server ps = new Server();
		Connector con = new SocketConnector();
		con.setPort(9090);
		QueuedThreadPool btp = new QueuedThreadPool();
        btp.setMinThreads(1);
        btp.setMaxThreads(10);
        btp.setMaxIdleTimeMs(300 * 1000);
        ps.setThreadPool(btp);
        ps.addConnector(con);
        Context context = new Context();
        HandlerWrapper handler = new HandlerWrapper();
        context.setContextPath("/");
        context.addHandler(handler);
        ps.addHandler(context);
        ps.start();
        
        Thread.sleep(10 * 1000);
        ps.stop();
	}
	
	private static void addServlet2(Server server){
		Context ctx = new Context();
		ctx.setContextPath("/");
		ServletHandler servletHandler = new ServletHandler();
		//org.mortbay.jetty.plus.servlet.ServletHandler servletHandler = new org.mortbay.jetty.plus.servlet.ServletHandler();
		//servletHandler.addServlet(new ServletHolder(TestServlet1.class));
		servletHandler.addServletWithMapping(TestServlet1.class,"/servlet_new");
		servletHandler.addServletWithMapping(TestServlet1.class, "/servlet2_new");
		servletHandler.addServletWithMapping(TestServlet2.class, "/sr_new");
		servletHandler.addServletWithMapping(ChunkTest.class, "/chunk_new");
		servletHandler.addServletWithMapping(TestServlet1.class,"/reflect-raw_new");
		servletHandler.addServletWithMapping(SpitServlet.class,"/httpperf/TestServlet_new");
		ctx.setServletHandler(servletHandler);
		server.addHandler(ctx);
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return jettyServerParams.getPort();
	}
	
}
