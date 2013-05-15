package coursera.alg1.lect4;

import org.junit.Assert;

public abstract class MaxPQTest<T extends MaxPriorityQueue<Integer>> extends PQTest<T>{

	public MaxPQTest(int count, T maxPQ) {
		super(count, maxPQ);
	}
	

	public void testQueue(int[] array) {
		onlyMinElements(array, CAPACITY);
		
		assertState( CAPACITY);
	}

	private void assertState(int minNumber) {
		System.out.println(pq);
		for (int i = minNumber-1; i >= 0; i--) {
			Assert.assertEquals(i,pq.delMax().intValue() );
		}
	}

	private void onlyMinElements(int[] array, int minNumber) {
		for (int i = 0; i < array.length; i++) {
			pq.insert(array[i]);
			if (pq.size() > minNumber) {
				pq.delMax();
			}
		}
	}
}
