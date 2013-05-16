package test.yourkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) throws IOException {
		List<Object> list = new ArrayList<Object>();
		System.in.read();
		for(int i=0;i<100000;i++){
			Object a =new Object();
			list.add(a);
			System.out.println(a.toString());
		}
	}
}
