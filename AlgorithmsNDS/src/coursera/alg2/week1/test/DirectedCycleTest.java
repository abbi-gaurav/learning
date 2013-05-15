package coursera.alg2.week1.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.DiGraph;
import coursera.alg2.week1.DirectedCycle;

@RunWith(Parameterized.class)
public class DirectedCycleTest {
	
	private final DiGraph diGraph;
	private final boolean hasCycle;

	public DirectedCycleTest(String fileName, boolean isCycle) {
		this.diGraph = (DiGraph) GraphTestUtils.createGraph(fileName, true);
		this.hasCycle = isCycle;
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][] { { "tinyDG.txt", true },
				{ "tinyDAG.txt", false } });
	}
	
	@Test
	public void test(){
		DirectedCycle cycle = new DirectedCycle(diGraph);
		Assert.assertEquals(hasCycle, cycle.hasCycle());
		if(hasCycle){
			GraphTestUtils.printPath(cycle.cycle());
		}
	}
}
