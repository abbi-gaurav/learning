package coursera.alg2.week1.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import coursera.alg2.week1.AdjacencyListGraph;
import coursera.alg2.week1.BreadthFirstPath;
import coursera.alg2.week1.DiGraph;
import coursera.alg2.week1.Graph;
import coursera.alg2.week1.GraphSearches;
import coursera.alg2.week2.DiagEdgeWtdSP;
import coursera.alg2.week2.DirectedEdge;
import coursera.alg2.week2.Edge;
import coursera.alg2.week2.EdgeWeightedDigraph;
import coursera.alg2.week2.EdgeWeightedGraph;

import junit.framework.Assert;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class GraphTestUtils {

	public static final String DIR = "/home/gaurav_abbi/learning/coursera/data/algs4-data/";

	public static Graph createGraph(String fileName, boolean isDirected) {
		In in = new In(DIR+fileName);
		Graph graph = isDirected ? new DiGraph(in.readInt()): new AdjacencyListGraph(in.readInt());
	
		int edges = in.readInt();
		for (int i = 0; i < edges; i++) {
			graph.addEdge(in.readInt(), in.readInt());
		}
		return graph;
	}

	public static void printPath(Iterable<Integer> path) {
		if(path == null){
			System.out.println(path);
			return;
		}
		
		for (int v : path) {
			System.out.print(v+"-");
		}
		System.out.println();
	}
	
	public static void verifyConnectedVertices(Graph graph, GraphSearches dfs,
			BreadthFirstPath bfs, int source, int pathTo) {
		List<Integer> bfsRes = getConnectedVertices(graph, bfs, pathTo);
		Collections.sort(bfsRes);
		System.out.println("BFS Vertices connected to "+source+"----"+bfsRes);
		
		List<Integer> dfsRes = getConnectedVertices(graph, dfs,source);
		Collections.sort(dfsRes);
		System.out.println("DFS Vertices connected to "+source+"----"+dfsRes);
		
		Assert.assertEquals(bfsRes,dfsRes);
	}
	
	private static List<Integer> getConnectedVertices(Graph graph, GraphSearches path, int s) {
		
		List<Integer> list = new ArrayList<>();
		for(int i=0;i<graph.getV();i++){
			if(path.hasPathTo(i)){
				list.add(i);
			}
		}
		return list;
	}
	
	public static EdgeWeightedGraph createEWGraph(String fileName){
		In in = new In(DIR+fileName);
		EdgeWeightedGraph ewGraph = new EdgeWeightedGraph(in.readInt());
		int edges = in.readInt();
		
		for(int i = 0; i<edges;i++){
			ewGraph.addEdge(new Edge(in.readInt(), in.readInt(), in.readDouble()));
		}
		return ewGraph;
	}
	
	public static <T>  void  printIterable(Iterable<T> iterable){
		for(T t:iterable){
			System.out.println(t);
		}
	}

	public static EdgeWeightedDigraph createEWDiGraph(String fileName) {
		In in = new In(DIR+fileName);
		EdgeWeightedDigraph ewDiGraph = new EdgeWeightedDigraph(in.readInt());
		int edges = in.readInt();
		
		for(int i = 0; i<edges;i++){
			ewDiGraph.addEdge(new DirectedEdge(in.readInt(), in.readInt(), in.readDouble()));
		}
		return ewDiGraph;
	}

	public static void printSPPathForEWD(EdgeWeightedDigraph graph2, int s,
			DiagEdgeWtdSP sp) {
		for (int v = 0; v < graph2.getV(); v++) {
			StdOut.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
			if(sp.hasPath(v)){
				for (DirectedEdge e : sp.path(v)){
					StdOut.print(e + " ");
				}
			}
			StdOut.println();
		}
	}

}
