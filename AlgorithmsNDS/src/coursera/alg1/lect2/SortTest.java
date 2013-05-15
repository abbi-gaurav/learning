package coursera.alg1.lect2;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg1.lect4.TestHelper;

@RunWith(Parameterized.class)
public abstract class SortTest<T extends Sort<Double>> {

	private final Double[] doubles;
	protected final T sort;

	public SortTest(int count, T sort) {
		doubles = TestHelper.getArray(count);
		this.sort = sort;
	}

	@Parameters
	public static Collection<Object[]> getParameters(){
		return SortTesterSuite.getCounts();
	}
	
	@Test
	public void testSort(){
		sort.doSort(doubles);
	}
	
	@Test
	public void test10(){
		Double[] doublesLocal = TestHelper.getArray(10);
		sort.doSort(doublesLocal);
		for(int i = 1; i < doublesLocal.length;i++){
			Assert.assertTrue(doublesLocal[i] - doublesLocal[i-1] > 0.0);
		}
	}
	
}
