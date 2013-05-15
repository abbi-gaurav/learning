package coursera.alg2.week1;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import coursera.alg1.lect2.LinkedStack;
import coursera.alg1.lect2.Stack;;

public class BreadthFirstPath implements GraphSearches{
	private final boolean[] marked;
	private final int[] edgeTo;
	private final int source;
	private final int[] distTo;
	
	public BreadthFirstPath(Graph graph, int source) {
		int vertices = graph.getV();
		marked = new boolean[vertices];
		edgeTo = new int[vertices];
		this.source = source;
		this.distTo = new int[vertices];
		
		
		bfs(graph);
	}

	private void bfs(Graph graph) {
		Arrays.fill(distTo, -1);
		
		Queue<Integer> queue = new LinkedList<>();
		
		distTo[source] = 0;
		marked[source] = true;
		queue.add(source);
		
		while(!queue.isEmpty()){
			int v = queue.poll();
			
			for(int w:graph.adj(v)){
				if(!marked[w]){
					distTo[w] = distTo[v]+1;
					marked[w] = true;
					edgeTo[w] = v;
					queue.add(w);
				}
			}
		}
	}

	@Override
	public boolean hasPathTo(int v) {
		return marked[v];
	}

	@Override
	public Iterable<Integer> pathTo(int v) {
		if(!hasPathTo(v)){
			return null;
		}
		
		Stack<Integer> stack = new LinkedStack<>();
		for(int x=v; x != source; x = edgeTo[x]){
			stack.push(x);
		}
		stack.push(source);
		return stack;
	}
}
