package coursera.alg1.lect3;
import coursera.alg1.lect2.SelectionSort;
import coursera.alg1.lect2.SortTest;


public class InsertionSortTest extends SortTest<SelectionSort<Double>>{

	public InsertionSortTest(int count) {
		super(count, new SelectionSort<Double>());
	}

}
