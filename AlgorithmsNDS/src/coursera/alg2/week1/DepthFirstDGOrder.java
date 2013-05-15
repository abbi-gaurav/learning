package coursera.alg2.week1;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;;

public class DepthFirstDGOrder {
	
	private boolean[] marked;
	private final Stack<Integer> reversePostOrder = new LinkedStack<>();
	
	public DepthFirstDGOrder(DiGraph diGraph) {
		int vertices = diGraph.getV();
		this.marked = new boolean[vertices];
		
		for(int v=0; v<vertices;v++){
			if(!marked[v]){
				dfs(diGraph,v);
			}
		}
	}

	private void dfs(DiGraph diGraph, int v) {
		marked[v] = true;
		for(int w:diGraph.adj(v)){
			if(!marked[w]){
				dfs(diGraph, w);
			}
		}
		reversePostOrder.push(v);
	}

	public Iterable<Integer> getReversePostOrder() {
		return reversePostOrder;
	}
	
}
