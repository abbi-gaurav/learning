package coursera.alg2.week2;

import coursera.alg1.lect1.WtdQuickUnion;
import coursera.alg1.lect2.LinkedQueue;
import coursera.alg1.lect4.BHBasedMinPrioritQ;
import coursera.alg1.lect4.MinPriorityQueue;

public class KruskalMST implements MST {
	public final LinkedQueue<Edge> mst = new LinkedQueue<>();
	private double mstWeight;
	
	public KruskalMST(EdgeWeightedGraph ewGraph) {
		MinPriorityQueue<Edge> minQ = new BHBasedMinPrioritQ<>(ewGraph.edgesCount());
		
		for(Edge e:ewGraph.edges()){
			minQ.insert(e);
		}
		
		int vertices = ewGraph.getV();
		WtdQuickUnion quickUnion = new WtdQuickUnion(vertices);
		
		while(!minQ.isEmpty() && mst.size() < vertices-1){
			Edge edge = minQ.delMin();
			
			int v = edge.either();
			int w = edge.other(v);
			
			if(!quickUnion.isConnected(v, w)){
				quickUnion.union(v, w);
				mst.enqueue(edge);
				mstWeight += edge.getWeight();
			}
		}
	}
	
	@Override
	public Iterable<Edge> edges(){
		return mst;
	}
	
	@Override
	public double getWeight(){
		return mstWeight;
	}
}
