package coursera.alg1.lect4;

public class BinaryMaxPQTest extends MaxPQTest<BinaryHeapBasedMaxPQ<Integer>>{
	public BinaryMaxPQTest(int count) {
		super(count, new BinaryHeapBasedMaxPQ<Integer>(MaxPQTest.CAPACITY+1));
	}
}
