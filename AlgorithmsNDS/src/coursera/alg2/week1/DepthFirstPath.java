package coursera.alg2.week1;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;;

public class DepthFirstPath implements GraphSearches {
	
	private final int source;
	private final boolean[] marked;
	private final int[] edgeTo;
	
	public DepthFirstPath(Graph graph, int s){
		this.source = s;
		this.marked = new boolean[graph.getV()];
		this.edgeTo = new int[graph.getV()];
		
		dfs(graph, s);
	}
	
	private void dfs(Graph graph, int v) {
		marked[v] = true;
		for (int w : graph.adj(v)) {
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(graph, w);
			}
		}
	}

	@Override
	public boolean hasPathTo(int v){
		return marked[v];
	}
	
	@Override
	public Iterable<Integer> pathTo(int v){
		if(!hasPathTo(v)){
			return null;
		}
		Stack<Integer> stack = new LinkedStack<>();
		for(int x = v; x != source; x = edgeTo[x]){
			stack.push(x);
		}
		stack.push(source);
		return stack;
	}
}
