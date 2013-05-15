package coursera.alg2.week2.test;

import coursera.alg2.week1.test.GraphTestUtils;
import coursera.alg2.week2.KruskalMST;

public class KruskalTest extends MSTTest<KruskalMST>{
	
	public KruskalTest(String file, double expectedMSTWeight) {
		super(new KruskalMST(GraphTestUtils.createEWGraph(file)), expectedMSTWeight);
	}
}
