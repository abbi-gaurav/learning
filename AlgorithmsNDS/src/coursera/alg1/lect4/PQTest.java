package coursera.alg1.lect4;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public abstract class PQTest<T extends PriorityQueue<Integer>> {
	static final int CAPACITY = 7;
	
	private int count;
	final T pq;
	
	@Parameters
	public static Collection<Object[]> getParams(){
		Object[][] params = new Object[][]{{16}};
		return Arrays.asList(params);
	}
	
	public PQTest(int count, T maxPQ) {
		this.count = count;
		this.pq = maxPQ;
	}
	
	@Test
	public void testOperation() {
		int[] array = TestHelper.getShuffledArray(count);

		testQueue(array);
	}

	public abstract void testQueue(int[] array);
}
