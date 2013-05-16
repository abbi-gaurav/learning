package com.test.java.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class JavaHttpServer {
	protected final HttpServer server;

	public JavaHttpServer(int port) throws IOException {
		this(HttpServer.create(new InetSocketAddress("localhost", port), 100));
		
	}
	protected JavaHttpServer(HttpServer server) {
		this.server = server;
		server.createContext("/testHttpSender", new TestHandler());
		server.setExecutor(Executors.newCachedThreadPool());
	}
	public void start(){
		server.start();
	}
	
	public static void main(String[] args) throws Exception {
		new JavaHttpServer(25000).start();
	}
}
