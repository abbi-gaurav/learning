package coursera.alg2.week1;


public class StronglyConnectedDiGraph {
	private final int[] stcc;
	private final boolean[] marked;
	private int count;
	public StronglyConnectedDiGraph(DiGraph diGraph) {
		Iterable<Integer> postOrderReverse = new DepthFirstDGOrder(
				diGraph.reverse()).getReversePostOrder();
		int vertices = diGraph.getV();
		stcc = new int[vertices];
		marked = new boolean[vertices];
		
		for(int v:postOrderReverse){
			if(!marked[v]){
				dfs(v,diGraph);
				count++;
			}
		}
	}

	private void dfs(int v, DiGraph diGraph) {
		stcc[v] = count;
		marked[v] = true;
		for (int w : diGraph.adj(v)) {
			if (!marked[w]) {
				dfs(w, diGraph);
			}
		}
	}
	
	public boolean stronglyConnected(int v, int w){
		return stcc[v] == stcc[w];
	}
	
	public int count(){
		return count;
	}
}
