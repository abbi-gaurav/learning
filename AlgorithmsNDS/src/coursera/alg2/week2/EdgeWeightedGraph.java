package coursera.alg2.week2;

import coursera.alg2.week1.Bag;


public class EdgeWeightedGraph {
	private final int vertices;
	private final coursera.alg2.week1.Bag<Edge>[] adjacents;
	private int edges;
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int vertices) {
		this.vertices = vertices;
		this.adjacents = (Bag<Edge>[])new Bag[vertices];
		for(int i=0;i<vertices;i++){
			adjacents[i] = new Bag<>();
		}
	}
	
	public int getV(){
		return vertices;
	}
	
	public void addEdge(Edge e){
		int v = e.either();
		int w = e.other(v);
		adjacents[v].add(e);
		adjacents[w].add(e);
		edges++;
	}
	
	public Iterable<Edge> adj(int v){
		return adjacents[v];
	}
	
	public Iterable<Edge> edges(){
		Bag<Edge> bag = new Bag<>();
		for(int v=0;v<this.vertices;v++){
			for(Edge e:adj(v)){
				if(e.other(v) > v){
					bag.add(e);
				}
			}
		}
		return bag;
	}
	
	public int edgesCount(){
		return edges;
	}

}
