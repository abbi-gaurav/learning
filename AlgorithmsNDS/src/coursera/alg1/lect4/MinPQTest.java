package coursera.alg1.lect4;

import org.junit.Assert;

public abstract class MinPQTest<T extends MinPriorityQueue<Integer>> extends
		PQTest<T> {

	public MinPQTest(int count, T pq) {
		super(count, pq);
	}

	@Override
	public void testQueue(int[] array) {
		onlyMaxElements(array, CAPACITY);
		assertState(array.length - 1);
	}

	private void assertState(int max) {
		System.out.println(pq);
		for (int i = max - CAPACITY + 1; i <= max; i++) {
			Assert.assertEquals(i, pq.delMin().intValue());
		}
	}

	private void onlyMaxElements(int[] array, int capacity) {
		for (int i = 0; i < array.length; i++) {
			pq.insert(array[i]);
			if (pq.size() > capacity) {
				pq.delMin();
			}
		}
	}
}
