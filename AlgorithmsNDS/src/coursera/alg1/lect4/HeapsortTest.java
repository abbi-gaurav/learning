package coursera.alg1.lect4;

import coursera.alg1.lect2.SortTest;

public class HeapsortTest extends SortTest<HeapSort<Double>>{

	public HeapsortTest(int count) {
		super(count, new HeapSort<Double>());
	}

}
