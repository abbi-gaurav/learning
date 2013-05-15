package coursera.alg2.week1;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;;

public class BipartiteQ {

	private final boolean[] marked;
	private final boolean[] colors;
	private final int[] edgeTo;
	private final boolean isBipartite;
	private Stack<Integer> cycle;
	
	public BipartiteQ(Graph graph) {
		int vertices = graph.getV();

		marked = new boolean[vertices];
		edgeTo = new int[vertices];

		colors = new boolean[vertices];

		for(int v=0; v<vertices;v++){
			if(!marked[v] && !dfs(graph,v)){
				isBipartite = false;
				return;
			}
		}

		isBipartite = true;
	}

	private boolean dfs(Graph graph, int v) {
		marked[v] = true;
		for(int w:graph.adj(v)){
			
			if(cycle != null){
				return false;
			}
			
			if(!marked[w]){
				colors[w] = !colors[v];
				edgeTo[w] = v;
				dfs(graph, w);
			}else if(colors[v] == colors[w]){
				cycle = new LinkedStack<>();
				cycle.push(w);
				for (int x = v; x != w; x=edgeTo[x]) {
					cycle.push(x);
				}
				cycle.push(w);
				return false;
			}
		}
		return true;
	}
	
	public boolean isBipartite(){
		return isBipartite;
	}
	
	public Iterable<Integer> cycle(){
		return cycle;
	}
	
	public boolean color(int v){
		if(isBipartite){
			return colors[v];
		}
		
		throw new IllegalStateException("Not bipartited");
	}
}
