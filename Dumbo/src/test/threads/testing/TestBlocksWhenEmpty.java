package test.threads.testing;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestBlocksWhenEmpty {
	private static final long TIME_OUT = 3 * 1000;
	private final BoundBuffer<Integer> buffer = new BoundBuffer<Integer>(10);
	
	@Test(testName="testblock")
	public void test(){	
		Thread taker = new Thread(){
			public void run() {
				try{
					buffer.take();
					Assert.fail();
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		try{
			taker.start();
			Thread.sleep(TIME_OUT);
			taker.interrupt();
			taker.join(TIME_OUT);
			Assert.assertFalse(taker.isAlive());
		}catch (Exception e) {
			Assert.fail("no error shud be here",e);
		}
	}
}
