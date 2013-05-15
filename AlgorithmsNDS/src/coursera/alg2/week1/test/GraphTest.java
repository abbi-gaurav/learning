package coursera.alg2.week1.test;

import junit.framework.Assert;

import org.junit.Test;

import coursera.alg2.week1.BreadthFirstPath;
import coursera.alg2.week1.ConnectedComponents;
import coursera.alg2.week1.DepthFirstPath;
import coursera.alg2.week1.GraphSearches;

import edu.princeton.cs.introcs.StdRandom;

public class GraphTest extends GraphTestBase{
	public GraphTest(String fileName) {
		super(fileName);
	}
	
	@Test
	public void comparePathSearches(){
		int vertices = graph.getV();
		int source = StdRandom.uniform(vertices);
		int pathTo = StdRandom.uniform(vertices);
		
		GraphSearches dfs = new DepthFirstPath(graph, source);
		findPath(pathTo, dfs);
		
		BreadthFirstPath bfs = new BreadthFirstPath(graph, source);
		findPath(pathTo, bfs);
		
		GraphTestUtils.verifyConnectedVertices(graph,dfs,bfs,source,pathTo);
		
		verifyConnectedComponent(dfs,bfs, source);
	}

	private void verifyConnectedComponent(GraphSearches dfs,
			BreadthFirstPath bfs, int source) {
		int vertices = graph.getV();
		ConnectedComponents cc = new ConnectedComponents(graph);
		for(int i=0;i<10;i++){
			int number = StdRandom.uniform(vertices);
			Assert.assertEquals(bfs.hasPathTo(number), dfs.hasPathTo(number));
			Assert.assertEquals(bfs.hasPathTo(number), cc.connected(source, number));
		}
	}

	private void findPath(int pathTo, GraphSearches searcher) {
		Iterable<Integer> dfsPath = searcher.pathTo(pathTo);
		GraphTestUtils.printPath(dfsPath);
	}
	
}
