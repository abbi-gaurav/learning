package coursera.alg2.week2.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.test.GraphTestUtils;
import coursera.alg2.week2.DiagEdgeWtdSP;
import coursera.alg2.week2.EdgeWeightedDigraph;
import coursera.alg2.week2.EdgeWtdDAGSP;
import edu.princeton.cs.introcs.StdRandom;

@RunWith(Parameterized.class)
public class EdgeWtdDAGSPTest {
	private EdgeWeightedDigraph graph;

	public EdgeWtdDAGSPTest(String fileName) {
		this.graph = GraphTestUtils.createEWDiGraph(fileName);
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{{"tinyEWDAG.txt"}});
	}
	
	@Test
	public void test(){
		int vertices = graph.getV();
		int s = StdRandom.uniform(vertices);
		
		DiagEdgeWtdSP mySP = new EdgeWtdDAGSP(graph, s );
		
		GraphTestUtils.printSPPathForEWD(graph, s, mySP);
	}
}
