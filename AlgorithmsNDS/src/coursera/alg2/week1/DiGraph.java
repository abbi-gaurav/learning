package coursera.alg2.week1;

public class DiGraph extends AdjacencyListGraph{

	public DiGraph(int v) {
		super(v);
	}
	
	@Override
	public void addEdge(int v, int w) {
		adjacents[v].add(w);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int v=0;v<getV();v++){
			for(int w:adj(v)){
				sb.append(v+"-->"+w+"\n");
			}
		}
		return sb.toString();
	}
	
	public DiGraph reverse(){
		int vertices = getV();
		DiGraph reverse = new DiGraph(vertices);
		
		for(int vertex = 0; vertex < vertices;vertex++){
			for(int w:adj(vertex)){
				reverse.addEdge(w, vertex);
			}
		}
		return reverse;
	}
}
