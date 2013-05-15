package coursera.alg1.lect1;

public interface Unionable {

	public abstract boolean isConnected(int p, int q);

	public abstract void union(int p, int q);

}