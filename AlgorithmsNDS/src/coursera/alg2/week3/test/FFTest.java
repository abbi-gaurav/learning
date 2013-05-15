package coursera.alg2.week3.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week3.FlowNetwork;
import coursera.alg2.week3.FordFulkerson;

@RunWith(Parameterized.class)
public class FFTest {
	private FlowNetwork graph;

	public FFTest(String fileName) {
		this.graph = Utils.create(fileName);
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{{"lectureFN.txt"}});
	}
	
	@Test
	public void runFF(){
		System.out.println(new FordFulkerson(graph, 0, graph.getV()-1).value());
	}
}
