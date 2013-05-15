package coursera.alg2.week2;

import coursera.alg2.week1.Bag;

public class EdgeWeightedDigraph {
	private final Bag<DirectedEdge>[] adjacents;

	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(int v) {
		this.adjacents = (Bag<DirectedEdge>[])new Bag[v];
		
		for (int i = 0; i < v; i++) {
			adjacents[i] = new Bag<>();
		}
	}
	
	public void addEdge(DirectedEdge edge){
		adjacents[edge.from()].add(edge);
	}
	
	public Iterable<DirectedEdge> adj(int v){
		return adjacents[v];
	}

	public int getV() {
		return adjacents.length;
	}
}
