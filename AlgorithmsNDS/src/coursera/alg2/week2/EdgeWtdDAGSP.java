package coursera.alg2.week2;

import java.util.Arrays;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;

public class EdgeWtdDAGSP implements DiagEdgeWtdSP {
	
	private final double[] distTo;
	private final DirectedEdge[] edgeTo;

	public EdgeWtdDAGSP(EdgeWeightedDigraph graph, int s) {
		int vertices = graph.getV();
		
		this.distTo = new double[vertices];
		Arrays.fill(distTo, Double.POSITIVE_INFINITY);
		distTo[s] = 0.0;
		
		this.edgeTo = new DirectedEdge[vertices];
		
		TopologicalSortEWDG topologicalSort = new TopologicalSortEWDG(graph);
		
		if(!topologicalSort.isCyclic()){
			for(int v:topologicalSort.order()){
				for(DirectedEdge e:graph.adj(v)){
					relax(e);
				}
			}
		}else{
			throw new IllegalArgumentException("Not acyclic");
		}
	}

	private void relax(DirectedEdge e) {
		int v = e.from();
		int w = e.to();
		
		if(distTo[w] > distTo[v]+e.weight()){
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
		}
	}
	
	@Override
	public boolean hasPath(int v){
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	
	@Override
	public Iterable<DirectedEdge> path(int v){
		if(!hasPath(v)){
			return null;
		}
		
		Stack<DirectedEdge> path = new LinkedStack<>();
		
		for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]){
			path.push(e);
		}
		
		return path;
	}

	@Override
	public double distTo(int v) {
		return distTo[v];
	}
	
}
