package learn.memory;

import java.util.ArrayList;

public class Leaker {
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Long> list = new ArrayList<Long>();
		Thread.sleep(10000); // 5s to allow starting visualvm
		for (long l = 0; l < Long.MAX_VALUE; l++) {
			list.add(new Long(l));
			for (int i = 0; i  < 5000; i++) {
				// busy wait, 'cause 1ms sleep is too long
			}
		}
	}
}
