package com.test.jetty.io;

import java.io.IOException;

import org.mortbay.jetty.nio.AbstractNIOConnector;

public class MyConnector extends AbstractNIOConnector{

	@Override
	protected void accept(int acceptorID) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	
	public void open() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public Object getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

}
