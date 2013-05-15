package coursera.alg2.week5.test;

import java.io.BufferedOutputStream;
import java.io.IOException;

import org.junit.Test;

import coursera.alg2.week5.LZW;

public class LZWTest {

	@Test
	public void test() throws IOException{
		new BufferedOutputStream(System.out).write("ABABABA".getBytes());
		
		LZW.compress();
		LZW.expand();
	}
}
