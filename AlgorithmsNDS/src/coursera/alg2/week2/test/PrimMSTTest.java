package coursera.alg2.week2.test;

import coursera.alg2.week1.test.GraphTestUtils;
import coursera.alg2.week2.PrimLazyMST;

public class PrimMSTTest extends MSTTest<PrimLazyMST>{
	
	public PrimMSTTest(String file, double expectedMSTWeight) {
		super(new PrimLazyMST(GraphTestUtils.createEWGraph(file)), expectedMSTWeight);
	}
}
