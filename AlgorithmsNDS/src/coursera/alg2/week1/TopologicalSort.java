package coursera.alg2.week1;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;;

public class TopologicalSort {
	
	private final boolean[] marked;
	private Stack<Integer> reverseSortOrder;
	private final boolean[] onDFS;
	private boolean isCyclic;
	
	public TopologicalSort(DiGraph directedGraph) {
		int vertices = directedGraph.getV();
		this.marked = new boolean[vertices];
		this.reverseSortOrder = new LinkedStack<>();
		this.onDFS = new boolean[vertices];
		
		for(int v=0; v<vertices;v++){
			if(isCyclic){
				return;
			}
			if(!marked[v]){
				dfs(directedGraph,v);
			}
		}
	}

	private void dfs(DiGraph diGraph, int v) {
		marked[v] = true;
		onDFS[v] = true;
		
		for(int w:diGraph.adj(v)){
			if(!marked[w]){
				dfs(diGraph, w);
			}else if(onDFS[w]){
				isCyclic = true;
				return;
			}
		}
		reverseSortOrder.push(v);
		
		onDFS[v] = false;
	}
	
	public Iterable<Integer> topologicalOrder(){
		if(isCyclic){
			throw new IllegalStateException("graph is cyclic");
		}
		return reverseSortOrder;
	}
}
