package coursera.alg2.week2;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;

public class TopologicalSortEWDG {
	
	private boolean[] marked;
	private Stack<Integer> order = new LinkedStack<>();
	private boolean[] onStack;
	private boolean isCyclic;
	
	public TopologicalSortEWDG(EdgeWeightedDigraph graph) {
		int vertices = graph.getV();
		this.marked = new boolean[vertices];
		this.onStack = new boolean[vertices];
		for(int v=0; v<vertices;v++){
			if(isCyclic){
				return;
			}
			
			if(!marked[v]){
				dfs(v,graph);
			}
		}
	}

	private void dfs(int v, EdgeWeightedDigraph graph) {
		onStack[v] = true;
		marked[v] = true;
		
		for(DirectedEdge e:graph.adj(v)){
			int w = e.to();
			if(!marked[w]){
				dfs(w, graph);
			}else if(onStack[w]){
				isCyclic = true;
				return;
			}
		}
		order.push(v);
		
		onStack[v] = false;
	}
	
	public Iterable<Integer> order(){
		if(isCyclic){
			throw new IllegalStateException("No order for cyclic graph");
		}
		return order;
	}
	
	public boolean isCyclic(){
		return isCyclic;
	}
}
