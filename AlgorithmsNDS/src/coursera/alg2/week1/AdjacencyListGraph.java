package coursera.alg2.week1;

public class AdjacencyListGraph implements Graph {
	private final int v;
	final Bag<Integer>[] adjacents;

	@SuppressWarnings("unchecked")
	public AdjacencyListGraph(int v) {
		this.v = v;
		this.adjacents = (Bag<Integer>[]) new Bag[v];
		for (int i = 0; i < v; i++) {
			adjacents[i] = new Bag<>();
		}
	}

	@Override
	public void addEdge(int v, int w) {
		adjacents[v].add(w);
		adjacents[w].add(v);
	}

	@Override
	public Iterable<Integer> adj(int v) {
		return adjacents[v];
	}

	@Override
	public int getV() {
		return v;
	}

	@Override
	public int edges() {
		int count = 0;
		for (Bag<Integer> bag : adjacents) {
			count += bag.size();
		}
		return (count / 2);
	}

	public int degree(int v) {
		return this.adjacents[v].size();
	}

	public int maxDegree() {
		int max = 0;
		for (int i = 0; i < v; i++) {
			int degree = degree(i);
			if (degree > max) {
				max = degree;
			}
		}
		return max;
	}

	public double averageDegree() {
		return 2.0 * (edges() / getV());
	}

	public int noOfSelfLoops() {
		int count = 0;

		for (int i = 0; i < v; i++) {
			for (int w : adjacents[i]) {
				if (v == w) {
					count++;
				}
			}
		}
		return count;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i =0; i<v;i++){
			for(int w:adj(i)){
				sb.append(i+"-"+w+"\n");
			}
		}
		return sb.toString();
	}
}
