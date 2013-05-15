package coursera.alg1.lect4;

public class UOMaxPQTest extends MaxPQTest<UnorderedMaxPQ<Integer>>{
	
	public UOMaxPQTest(int count) {
		super(count, new UnorderedMaxPQ<Integer>(MaxPQTest.CAPACITY+1));
	}
}
