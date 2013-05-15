package coursera.alg2.week1;

public class ConnectedComponents {
	private final boolean[] marked;
	private final int[] ccId;
	private int count;
	
	public ConnectedComponents(Graph graph) {
		int vertices = graph.getV();
		this.marked = new boolean[vertices];
		this.ccId = new int[vertices];
		
		for(int v=0; v<vertices;v++){
			if(!marked[v]){
				dfs(graph, v);
				count++;
			}
		}
	}

	private void dfs(Graph graph, int v) {
		ccId[v] = count;
		marked[v] = true;
		
		for(int w:graph.adj(v)){
			if(!marked[w]){
				dfs(graph, w);
			}
		}
	}
	
	public int count(){
		return count;
	}
	
	public int id(int v){
		return ccId[v];
	}
	
	public boolean connected(int v, int w){
		return ccId[v] == ccId[w];
	}
}
