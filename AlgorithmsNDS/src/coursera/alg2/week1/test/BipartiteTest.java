package coursera.alg2.week1.test;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import coursera.alg2.week1.AdjacencyListGraph;
import coursera.alg2.week1.BipartiteQ;
import coursera.alg2.week1.Graph;

import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;
@RunWith(Parameterized.class)
public class BipartiteTest{

	private Graph graph;

	public BipartiteTest(int v, int e, int f) {
		this.graph = createBPGraph(v, e, f);
	}
	
	@Parameters
	public static Collection<Object[]> params(){
		Object[][] params = new Integer[][]{{10,7,2},{16,11,5},{25,23,1}};
		return Arrays.asList(params);
	}
	@Test
	public void testBP(){
		BipartiteQ bp = new BipartiteQ(graph);
		if(bp.isBipartite()){
			Assert.assertNull(bp.cycle());
			printColor(graph, bp);
		}else{
			Assert.assertNotNull(bp.cycle());
			GraphTestUtils.printPath(bp.cycle());
		}
	}
	
	public static void main(String[] args) {
        // create random bipartite graph with V vertices and E edges; then add F random edges
        int V = Integer.parseInt(args[0]);
        int E = Integer.parseInt(args[1]);
        int F = Integer.parseInt(args[2]);

        Graph graph = createBPGraph(V, E, F);

        StdOut.println(graph);


        BipartiteQ b = new BipartiteQ(graph);
        if (b.isBipartite()) {
            printColor(graph, b);
        }
        else {
            StdOut.print("Graph has an odd-length cycle: ");
            for (int x : b.cycle()) {
                StdOut.print(x + " ");
            }
            StdOut.println();
        }
    }

	private static void printColor(Graph graph, BipartiteQ b) {
		StdOut.println("Graph is bipartite");
		for (int v = 0; v < graph.getV(); v++) {
		    StdOut.println(v + ": " + b.color(v));
		}
	}

	private static Graph createBPGraph(int V, int E, int F) {
		Graph graph = new AdjacencyListGraph(V);
        int[] vertices = new int[V];
        for (int i = 0; i < V; i++) vertices[i] = i;
        StdRandom.shuffle(vertices);
        for (int i = 0; i < E; i++) {
            int v = StdRandom.uniform(V/2);
            int w = StdRandom.uniform(V/2);
            graph.addEdge(vertices[v], vertices[V/2 + w]);
        }

        // add F extra edges
        for (int i = 0; i < F; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            graph.addEdge(v, w);
        }
		return graph;
	}
	
}
