package coursera.alg2.week3;

import coursera.alg2.week1.Bag;

public class FlowNetwork {
	private Bag<FlowEdge>[] adjacents;

	@SuppressWarnings("unchecked")
	public FlowNetwork(int v) {
		this.adjacents = new Bag[v];
		for(int i =0;i<v;i++){
			this.adjacents[i] = new Bag<>();
		}
	}
	
	public void addEdge(FlowEdge e){
		adjacents[e.from()].add(e);
		adjacents[e.to()].add(e);
	}
	
	public Iterable<FlowEdge> adj(int v){
		return adjacents[v];
	}

	public int getV() {
		return adjacents.length;
	}
}
