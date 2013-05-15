package coursera.alg2.week1;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;;

public class DirectedCycle {
	
	private final boolean[] marked;
	private boolean[] onStack;
	private Stack<Integer> cycle;
	private final int[] edgeTo;
	
	public DirectedCycle(DiGraph diGraph) {
		int vertices = diGraph.getV();
		marked = new boolean[vertices];
		edgeTo = new int[vertices];
		
		//clever
		onStack = new boolean[vertices];
		
		for(int v = 0; v<vertices;v++ ){
			if(hasCycle()){
				return;
			}
			if(!marked[v]){
				dfs(v,diGraph);
			}
		}
	}

	private void dfs(int v, DiGraph diGraph) {
		marked[v] = true;
		onStack[v] = true;
		
		for(int w:diGraph.adj(v)){
			if(!marked[w]){
				edgeTo[w] = v;
				dfs(w, diGraph);
			}else if(onStack[w]){
				//there is a cycle
				cycle = new LinkedStack<>();
				for(int x = v; x != w; x = edgeTo[x]){
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
		
		onStack[v] = false;
	}

	public boolean hasCycle() {
		return cycle != null;
	}
	
	public Iterable<Integer> cycle(){
		return cycle;
	}
}
