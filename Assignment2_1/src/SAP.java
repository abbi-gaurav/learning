public class SAP {
	private Digraph diGraph;

	public SAP(Digraph diGraph) {
		this.diGraph = new Digraph(diGraph);
	}

	/**
	 * length of shortest ancestral path between v and w; -1 if no such path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(int v, int w) {
		Tuple<Integer, Integer> closeset = closestTuple(v, w);
		return closeset.v == -1 ? -1 : closeset.d;
	}

	/**
	 * a common ancestor of v and w that participates in a shortest ancestral
	 * path; -1 if no such path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(int v, int w) {
		Tuple<Integer, Integer> closest = closestTuple(v, w);
		return closest.v;
	}

	/**
	 * length of shortest ancestral path between any vertex in v and any vertex
	 * in w; -1 if no such path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		Tuple<Integer, Integer> tuple = closestTuple(v, w);
		return tuple.v == -1 ? -1 : tuple.d;
	}

	/**
	 * a common ancestor that participates in shortest ancestral path; -1 if no
	 * such path
	 * 
	 * @param v
	 * @param w
	 * @return
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		Tuple<Integer, Integer> closest = closestTuple(v, w);
		return closest.v;
	}

	private Tuple<Integer, Integer> closestTuple(Iterable<Integer> v,
			Iterable<Integer> w) {

		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(diGraph,
				w);
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(diGraph,
				v);
		Tuple<Integer, Integer> closest;
		int wi = w.iterator().next();
		if(bfsv.hasPathTo(wi)){
			int distToW = bfsv.distTo(wi);
			closest =  new Tuple<Integer, Integer>(wi, distToW);
			if(distToW <= 1){
				return closest;
			}
		}else{
			 closest = new Tuple<Integer, Integer>(-1,Integer.MAX_VALUE);
		}
		
		boolean[] visited = new boolean[diGraph.V()];
		
		for (int vi : v) {
			if (!visited[vi]) {
				Tuple<Integer, Integer> ancestorTuple = closestTuple(vi, bfsw,
						bfsv, visited, closest);
				if (closest.d > ancestorTuple.d) {
					closest = ancestorTuple;
				}
			}
		}
		return closest;
	}

	private void validate(int v) {
		if (v < 0 || v > diGraph.V() - 1) {
			throw new IndexOutOfBoundsException("Out of range" + v);
		}
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}

	private Tuple<Integer, Integer> closestTuple(int v, int w) {
		validate(v);
		validate(w);

		BreadthFirstDirectedPaths bfsw = new BreadthFirstDirectedPaths(diGraph,
				w);
		BreadthFirstDirectedPaths bfsv = new BreadthFirstDirectedPaths(diGraph,
				v);
		Tuple<Integer, Integer> tuple;
		if(bfsv.hasPathTo(w)){
			int distToW = bfsv.distTo(w);
			tuple = new Tuple<Integer, Integer>(w, distToW);
			if(distToW <= 1){
				return tuple;
			}
		}else{
			tuple = new Tuple<Integer, Integer>(-1, Integer.MAX_VALUE);
		}
		boolean[] visited = new boolean[diGraph.V()];

		Tuple<Integer, Integer> closeset = closestTuple(v, bfsw, bfsv, visited,
				tuple);
		return closeset;
	}

	private Tuple<Integer, Integer> closestTuple(int v,
			BreadthFirstDirectedPaths bfsw, BreadthFirstDirectedPaths bfsv,
			boolean[] visited, Tuple<Integer, Integer> closeset) {

		visited[v] = true;
		if (bfsw.hasPathTo(v)) {
			int distToV = bfsv.distTo(v) + bfsw.distTo(v);
			if (distToV < closeset.d) {
				closeset.v = v;
				closeset.d = distToV;
			}
			if(distToV <= 1){
				return closeset;
			}
		}

		for (int vi : diGraph.adj(v)) {
			if (!visited[vi]) {
				closeset =  closestTuple(vi, bfsw, bfsv, visited, closeset);
			}
		}
		return closeset;
	}

	private static class Tuple<I, J> {
		private I v;
		private J d;

		private Tuple(I i, J j) {
			this.v = i;
			this.d = j;
		}
	}
}
