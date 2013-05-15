package coursera.alg2.week1;

public interface Graph {

	void addEdge(int v, int w);

	Iterable<Integer> adj(int v);

	int getV();

	int edges();

}