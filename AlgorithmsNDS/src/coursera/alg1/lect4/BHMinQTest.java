package coursera.alg1.lect4;

public class BHMinQTest extends MinPQTest<BHBasedMinPrioritQ<Integer>>{

	public BHMinQTest(int count) {
		super(count, new BHBasedMinPrioritQ<Integer>(CAPACITY+1));
	}
}
