package coursera.alg1.lect2;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import coursera.alg1.lect3.BUMergeSortTest;
import coursera.alg1.lect3.MergeSortTest;
import coursera.alg1.lect3.QuickSortTest;
import coursera.alg1.lect3.ThreeWayQuickSortTest;
import coursera.alg1.lect4.HeapsortTest;

@RunWith(Suite.class)
@SuiteClasses({ MergeSortTest.class, ShellSortTest.class,
		/*InsertionSortTest.class, SelectionSortTest.class,*/ 
		BUMergeSortTest.class, QuickSortTest.class, ThreeWayQuickSortTest.class,
		HeapsortTest.class})
public class SortTesterSuite {
	
	public static Collection<Object[]> getCounts() {
		Object[][] data = new Object[][] { { 32 }, { 64 }, { 128 }, { 256 },{ 1024 } 
				,{ 65536 }, { 131072 }, { 131072 * 2 }, { 131072 * 4 } };
		return Arrays.asList(data);
	}
	
	public static void testSort(Double[] doubles, Sort<Double> sorter) {
		sorter.doSort(doubles);
	}
}
