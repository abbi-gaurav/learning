package coursera.alg2.week2;

public interface DiagEdgeWtdSP {

	boolean hasPath(int v);

	Iterable<DirectedEdge> path(int v);

	double distTo(int v);

}