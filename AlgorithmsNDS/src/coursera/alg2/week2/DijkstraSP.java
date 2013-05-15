package coursera.alg2.week2;

import java.util.Arrays;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;
import coursera.alg1.lect4.IndexMinPQ;

public class DijkstraSP implements DiagEdgeWtdSP{
	private IndexMinPQ<Double> indexMinPQ;
	private final double[] distTo;
	private final DirectedEdge[] edgeTo;
	
	public DijkstraSP(EdgeWeightedDigraph ewDiGraph, int s){
		int vertices = ewDiGraph.getV();
		this.indexMinPQ = new IndexMinPQ<>(vertices);
		
		this.distTo = new double[vertices];
		Arrays.fill(distTo, Double.POSITIVE_INFINITY);
		
		this.edgeTo = new DirectedEdge[vertices];
		
		distTo[s] = 0.0;
		indexMinPQ.insert(s, distTo[s] );
		
		while(!indexMinPQ.isEmpty()){
			int v = indexMinPQ.delMin();
			
			for(DirectedEdge e:ewDiGraph.adj(v)){
				relax(e);
			}
		}
		
	}
	
	@Override
	public Iterable<DirectedEdge> path(int v){
		Stack<DirectedEdge> stack = new LinkedStack<>();
		for(DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]){
			stack.push(e);
		}
		return stack;
	}

	private void relax(DirectedEdge e) {
		int v = e.from();
		int w = e.to();
		
		if(distTo[w] > distTo[v]+e.weight()){
			edgeTo[w] = e;
			distTo[w] = distTo[v]+e.weight();
			
			if(indexMinPQ.contains(w)){
				indexMinPQ.decreaseKey(w, distTo[w]);
			}else{
				indexMinPQ.insert(w, distTo[w]);
			}
		}
	}

	public double distTo(int v) {
		return distTo[v];
	}

	@Override
	public boolean hasPath(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	
}
