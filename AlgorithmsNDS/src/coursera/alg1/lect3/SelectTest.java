package coursera.alg1.lect3;
import org.junit.Assert;
import org.junit.Test;

import coursera.alg1.lect4.TestHelper;


public class SelectTest {
	
	@Test
	public void testSelect(){
		Double[] doubles = TestHelper.getArray(1000);
		Double[] cloned =  doubles.clone();
		
		QuickSort<Double> sort = new QuickSort<>();
		sort.doSort(doubles);
		
		for(int i = 1; i < doubles.length; i *= 7) {
			Comparable<Double> actual = sort.select(cloned, i);
			Assert.assertEquals("failed for index["+i+"]",doubles[i], actual);
		}
	}
}
