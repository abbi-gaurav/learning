package coursera.alg2.week1;

import coursera.alg2.week1.test.GraphTestUtils;
import edu.princeton.cs.introcs.StdOut;

public class DirectedDFS {
	private boolean[] marked;
	
	public DirectedDFS(DiGraph dig, int s){
		marked = new boolean[dig.getV()];
		dfs(dig,s);
	}
	
	public DirectedDFS(DiGraph dig, Iterable<Integer> sources) {
		this.marked = new boolean[dig.getV()];
		for(int s:sources){
			dfs(dig,s);
		}
	}
	
	public boolean hasPathTo(int v){
		return marked[v];
	}
	
	private void dfs(DiGraph dig, int v) {
		marked[v] = true;
		for(int w:dig.adj(v)){
			if(!marked[w]){
				dfs(dig, w);
			}
		}
	}
	
	// test client
    public static void main(String[] args) {

        // read in digraph from command-line argument
        DiGraph G = (DiGraph) GraphTestUtils.createGraph(args[0], true);

        // read in sources from command-line arguments
        Bag<Integer> sources = new Bag<Integer>();
        for (int i = 1; i < args.length; i++) {
            int s = Integer.parseInt(args[i]);
            sources.add(s);
        }

        // multiple-source reachability
        DirectedDFS dfs = new DirectedDFS(G, sources);

        // print out vertices reachable from sources
        for (int v = 0; v < G.getV(); v++) {
            if (dfs.hasPathTo(v)) StdOut.print(v + " ");
        }
        StdOut.println();
    }
}
