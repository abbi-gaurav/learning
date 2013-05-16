import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RQTestLL {
	private final Integer count;
	
	public RQTestLL(int count) {
		this.count = count;
	}

	@Parameters
	public static Collection<Object[]> count() {
		//increasing power of 2^1, 2^8, 2^16
		return TestSuite.getCount();
	}

	@Test
	public void randomCallsEnq() {
		RandomizedQueue<Integer> queue = getRQ();
		for (int i = 0; i < count; i++) {
			queue.enqueue(i);
		}

		for (int i = 0; i < count; i++) {
			queue.dequeue();
		}
	}

	@Test
	public void randomCallsEnqDeq() {
		RandomizedQueue<Integer> rq = getRQ();
		for (int i = 0; i < count; i++) {
			rq.enqueue(i);
			rq.enqueue(i);
			rq.enqueue(i);
			rq.enqueue(i);

			rq.dequeue();
			rq.dequeue();
			rq.dequeue();
			rq.dequeue();

			rq.enqueue(i);
			rq.enqueue(i);
		}
	}

	@Test
	public void randomCallsOnEmpty() {
		RandomizedQueue<Integer> rq = getRQ();
		for (int i = 0; i < 5; i++) {
			try {
				rq.dequeue();
			} catch (NoSuchElementException e) {
			}

			try {
				rq.sample();
			} catch (NoSuchElementException e) {
			}
		}
	}

	@Test
	public void testBoundry() {
		RandomizedQueue<Integer> rq = getRQ();
		for (int i = 0; i < count; i++) {
			rq.enqueue(i);
		}
		Integer v1 = rq.dequeue();
		Integer v2 = rq.dequeue();

		Assert.assertTrue(v1 + "::" + v2, !v1.equals(v2));
	}

	@Test
	public void testIterator() {
		RandomizedQueue<Integer> rq = getRQ();
		for (int i = 0; i < count; i++) {
			rq.enqueue(i);
		}
		Iterator<Integer> itr = rq.iterator();

		int numItems = 0;
		while (itr.hasNext()) {
			itr.next();
			numItems++;
		}

		Assert.assertEquals(count + "::" + numItems, count.intValue(), numItems);
	}
	
	RandomizedQueue<Integer> getRQ() {
		return new RandomizedQueueLLBased<>();
	}
}
