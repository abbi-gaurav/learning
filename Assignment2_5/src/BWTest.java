import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.Test;


public class BWTest {
	static{
		try {
			System.setIn(new FileInputStream("/home/gaurav_abbi/aesop2.txt.bwt"));
			System.setOut(new PrintStream("/home/gaurav_abbi/aesop2.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test(){
		BurrowsWheeler.decode();
	}
}
