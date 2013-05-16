package com.test.java.http.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TestHandler implements HttpHandler {
	private final TestHttpSenderServletHelper helper;

	public TestHandler() {
		helper = new TestHttpSenderServletHelper();
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String method = exchange.getRequestMethod().toUpperCase();
		HttpMethod methodEnum = HttpMethod.valueOf(method);
		String id = getId(exchange);
		System.out.println(id);
		
		if(methodEnum == HttpMethod.GET){
			exchange.sendResponseHeaders(200, 0);
		}
		processRequest(methodEnum, id, exchange);
	}

	private int processRequest(HttpMethod methodEnum, String id,
			HttpExchange exchange) throws IOException {
		switch (methodEnum) {
		case GET:
			helper.get(id, exchange.getResponseBody());
			return 200;
		case DELETE:
			return helper.delete(id)?200:500;
		default:
		case POST:
			helper.post(id, exchange.getRequestBody());
			return 200;
		}
	}

	private String getId(HttpExchange exchange)
			throws UnsupportedEncodingException {
		Map<String, String> queryParams = parseQuery(exchange.getRequestURI().getRawQuery());
		System.out.println(queryParams);
		String id = queryParams.get("key");
		return id;
	}
	
	private Map<String, String> parseQuery(String httpRequestParameterString) throws UnsupportedEncodingException{
		Map<String, String> map = new HashMap<String, String>();
		//check for blank
		String[] pairs = httpRequestParameterString.split("[&]");
		for (String pair : pairs) {
			String param[] = pair.split("[=]");
			if(param.length > 1){
				String property = System.getProperty("file.encoding");
				String key = URLDecoder.decode(param[0],property);
				String value = URLDecoder.decode(param[1],property);
				map.put(key, value);
			}
		}
		return map;
	}
}
