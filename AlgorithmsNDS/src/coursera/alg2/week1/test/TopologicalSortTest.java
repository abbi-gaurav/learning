package coursera.alg2.week1.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.DiGraph;
import coursera.alg2.week1.TopologicalSort;

@RunWith(Parameterized.class)
public class TopologicalSortTest {
	
	private final DiGraph diGraph;
	private final boolean cyclic;

	public TopologicalSortTest(String fileName, boolean isCyclic) {
		this.diGraph = (DiGraph) GraphTestUtils.createGraph(fileName, true);
		this.cyclic = isCyclic;
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][] { { "tinyDAG.txt", false },
				{ "tinyDG.txt", true } });
	}
	
	@Test
	public void test(){
		try{
			GraphTestUtils.printPath(new TopologicalSort(diGraph).topologicalOrder());
		}catch(IllegalStateException e){
			if(!cyclic){
				throw e;
			}
		}
	}
}
