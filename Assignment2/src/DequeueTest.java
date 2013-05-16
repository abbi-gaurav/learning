import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DequeueTest {
	
	private final int count;

	public DequeueTest(int count) {
		this.count = count;
	}
	
	@Parameters
	public static Collection<Object[]> count() {
		return TestSuite.getCount();
	}
	
	@Test
	public void testaddFirst() {
		Deque<Integer> dequeue = new Deque<>();
		add(dequeue);

		Iterator<Integer> iterator = dequeue.iterator();
		int entryNum = 0;

		entryNum = iterate(iterator, entryNum);
		Assert.assertEquals(count, entryNum);
	}

	private int iterate(Iterator<Integer> iterator, int entryNum) {
		while (iterator.hasNext()) {
			iterator.next();
			entryNum++;
		}
		return entryNum;
	}

	private void add(Deque<Integer> dequeue) {
		for (int i = 0; i < count; i++) {
			dequeue.addFirst(i);
		}
	}

}
