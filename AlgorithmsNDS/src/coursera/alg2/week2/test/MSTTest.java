package coursera.alg2.week2.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.test.GraphTestUtils;
import coursera.alg2.week2.MST;

@RunWith(Parameterized.class)
public abstract class MSTTest<T extends MST> {
	
	private T mst;
	private final double expectedMSTWt;

	public MSTTest(T mst, double expectedMSTWeight){
		this.mst = mst;
		this.expectedMSTWt = expectedMSTWeight;
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{{"tinyEWG.txt",1.81},{"mediumEWG.txt",10.46351}});
	}
	
	@Test
	public void test(){
		GraphTestUtils.printIterable(mst.edges());
		System.out.println(mst.getWeight());
		Assert.assertEquals(expectedMSTWt, mst.getWeight(),0.0000000001);
	}
}
