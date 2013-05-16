package com.test.jetty;

import org.mortbay.jetty.Server;

public interface IServer {
	/**
	 * this will start the jetty server 
	 */
	void start();
	
	/**
	 * this will shutdown the adapter
	 */
	void shutdown();
	
	Server getHttpServer();
}
