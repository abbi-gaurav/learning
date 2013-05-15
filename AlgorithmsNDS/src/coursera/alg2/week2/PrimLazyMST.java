package coursera.alg2.week2;

import coursera.alg1.lect2.LinkedQueue;
import coursera.alg1.lect4.BHBasedMinPrioritQ;
import coursera.alg1.lect4.MinPriorityQueue;

public class PrimLazyMST implements MST {
	private final LinkedQueue<Edge> mst = new LinkedQueue<>();
	private final boolean[] marked;
	private final MinPriorityQueue<Edge> minPQ;
	private double mstWeight;
	
	public PrimLazyMST(EdgeWeightedGraph ewGraph) {
		int vertices = ewGraph.getV();
		marked = new boolean[vertices];
		minPQ = new BHBasedMinPrioritQ<>(vertices);
		
		visit(ewGraph,0);
		
		while(!minPQ.isEmpty() && mst.size() < vertices-1){
			Edge e = minPQ.delMin();
			int v = e.either();
			int w = e.other(v);
			
			if(marked[v] &&  marked[w]){
				continue;
			}
			
			mst.enqueue(e);
			mstWeight += e.getWeight();
			if(!marked[v]){
				visit(ewGraph, v);
			}else{
				visit(ewGraph, w);
			}
		}
	}
	
	private void visit(EdgeWeightedGraph ewGraph, int v) {
		marked[v] = true;
		
		for(Edge e:ewGraph.adj(v)){
			if(!marked[e.other(v)]){
				minPQ.insert(e);
			}
		}
	}

	@Override
	public Iterable<Edge> edges() {
		return mst;
	}

	@Override
	public double getWeight() {
		return mstWeight;
	}

}
