package com.test.server;

import java.io.File;
import java.io.FileFilter;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;

public class ServerForAxis2 {
	public static void main(String[] args) throws Exception {
		int port = Integer.parseInt(args[0]);
		Server server = new Server();
		
		QueuedThreadPool tp = new QueuedThreadPool();
		tp.setMaxThreads(10);
		tp.setMinThreads(1);
		server.setThreadPool(tp);
		
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		server.addConnector(connector);
		
		deployAllWars(args[1],server);
		
		server.start();
		System.out.println(System.getProperty("java.class.path"));
	}

	private static void deployAllWars(String deployDirectory,Server server) {
		File directory = new File(deployDirectory);
		if(!directory.exists() || !directory.isDirectory()){
			throw new RuntimeException("illegal deploy directory"+deployDirectory);
		}
		
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isFile() && pathname.getName().endsWith(".war");
			}
		};
		
		File[] wars = directory.listFiles(filter);
		for(File war:wars){
			WebAppContext context = new WebAppContext();
			String warName = war.getName().substring(0, war.getName().length()-4);
			context.setContextPath("/"+warName);
			context.setExtractWAR(true);
			context.setWar(war.getAbsolutePath());
			context.setTempDirectory(new File(directory.getAbsolutePath()+"/"+warName));
			server.addHandler(context);
			context.setDefaultsDescriptor
			("/home/gauravabbi/old-lappy/srcCode/jetty6126-bin/jetty-6.1.26/etc/webdefault.xml");
			context.setParentLoaderPriority(false);
		}
	}
}
