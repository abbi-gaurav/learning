package com.test.jetty;


public class TestUri {
	private static final String URL = "https://[2001:db8:10:110::a75:2a3]:28188/hello HTTP/1.1";
	private static final String URL2 = "https://10.11.22.98:28188/hello HTTP/1.1";
	private static final String URL3 = "http://[2001:db8:10:110::a75:2a3]:38600/admin/Page?next=page.bpdmanagement";
	public static void main(String[] args) {
		//HttpURI uri = new HttpURI("https://[2001:db8:10:110::a75:2a3]:28188/hello HTTP/1.1");
		
		HttpURI uri = new HttpURI();
		uri.parse(URL3);
		//uri.parse(URL2);
		System.out.println(uri.getQuery());
	}
}
