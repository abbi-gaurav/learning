package coursera.alg2.week2.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.test.GraphTestUtils;
import coursera.alg2.week2.DiagEdgeWtdSP;
import coursera.alg2.week2.DijkstraSP;
import coursera.alg2.week2.EdgeWeightedDigraph;
import edu.princeton.cs.introcs.StdRandom;

@RunWith(Parameterized.class)
public class DijkstraTest {
	private EdgeWeightedDigraph graph;

	public DijkstraTest(String fileName) {
		this.graph = GraphTestUtils.createEWDiGraph(fileName);
	}

	@Parameters
	public static Collection<Object[]> params() {
		return Arrays.asList(new Object[][] { { "tinyEWD.txt" } });
	}

	@Test
	public void test() {
		EdgeWeightedDigraph graph2 = graph;
		int s = StdRandom.uniform(graph2.getV());
		DiagEdgeWtdSP sp = new DijkstraSP(graph2, s);

		GraphTestUtils.printSPPathForEWD(graph2, s, sp);

	}
}
