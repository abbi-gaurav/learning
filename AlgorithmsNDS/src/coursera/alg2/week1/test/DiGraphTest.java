package coursera.alg2.week1.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.BreadthFirstPath;
import coursera.alg2.week1.DepthFirstPath;
import coursera.alg2.week1.Graph;
import coursera.alg2.week1.GraphSearches;

import edu.princeton.cs.introcs.StdRandom;

@RunWith(Parameterized.class)
public class DiGraphTest {
	
	private final Graph directedGraph;

	public DiGraphTest(String fileName) {
		directedGraph = GraphTestUtils.createGraph(fileName, true);
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		return Arrays.asList(new Object[][]{{"tinyDG.txt"},{"tinyDAG.txt"}});
	}

	
	@Test
	public void comparePathSearches(){
		int vertices = directedGraph.getV();
		int source = StdRandom.uniform(vertices);
		int pathTo = StdRandom.uniform(vertices);
		
		DepthFirstPath dfs = new DepthFirstPath(directedGraph, source);
		verifySearch(source, pathTo, dfs);
		
		BreadthFirstPath bfs = new BreadthFirstPath(directedGraph, source);
		verifySearch(source, pathTo, bfs);
		
		GraphTestUtils.verifyConnectedVertices(directedGraph, dfs, bfs, source, pathTo);
	}

	private void verifySearch(int source, int pathTo, GraphSearches dfs) {
		System.out.println(source+"-->"+pathTo);
		if(dfs.hasPathTo(pathTo)){
			Iterable<Integer> path = dfs.pathTo(pathTo);
			Assert.assertNotNull(path);
			GraphTestUtils.printPath(path);
		}else{
			System.out.println("[]");
			Assert.assertNull(dfs.pathTo(pathTo));
		}
	}
}
