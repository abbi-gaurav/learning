package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Fun {
	public static void main(String[] args) throws UnknownHostException {
//		Object[] arr = new String[]{"hi","hi"};
//		arr[0] = new Integer(5);
		InetAddress address = InetAddress.getLocalHost();
		address.getHostName();
		System.out.println(address.getHostAddress());
		System.out.println(address.isLoopbackAddress());
	}
}
