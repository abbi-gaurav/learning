package com.test.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.component.AbstractLifeCycle;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;

public class TestHandler extends AbstractLifeCycle implements Handler{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, int dispatch) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		
	}

	public void setServer(Server server) {
		// TODO Auto-generated method stub
		
	}

}
