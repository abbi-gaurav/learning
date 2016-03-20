package com.test.server;

import java.util.Map;

import javax.servlet.http.HttpServlet;

public class JettyServerParameters {
	private final int port;
	private final String contextPath;
	private final Map<String, Class<? extends HttpServlet>> servletMap;
	private final int sslPort;

	public JettyServerParameters(int port, int sslPort, String contextPath,
			Map<String, Class<? extends HttpServlet>> servletMap) {
		this.port = port;
		this.sslPort = sslPort;
		this.contextPath = contextPath;
		this.servletMap = servletMap;
	}

	public int getPort() {
		return port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public Map<String, Class<? extends HttpServlet>> getServletMap() {
		return servletMap;
	}

	/**
	 * @return the sslPort
	 */
	public int getSslPort() {
		return sslPort;
	}
}