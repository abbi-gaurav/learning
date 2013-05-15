package coursera.alg1.lect3;
import coursera.alg1.lect2.SortTest;

public class MergeSortTest extends SortTest<MergeSort<Double>>{

	public MergeSortTest(int count) {
		super(count, new MergeSort<Double>());
	}
}
