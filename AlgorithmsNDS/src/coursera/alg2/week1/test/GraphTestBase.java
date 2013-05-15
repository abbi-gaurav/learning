package coursera.alg2.week1.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.Graph;


@RunWith(Parameterized.class)
public abstract class GraphTestBase {
	protected Graph graph;
	public GraphTestBase(String fileName) {
		this.graph = GraphTestUtils.createGraph(fileName, false);
	}

	@Parameters
	public static Collection<Object[]> params() {
		Object[][] params = new String[][] { { "tinyG.txt" }, { "tinyCG.txt" } };
		return Arrays.asList(params);
	}

}
