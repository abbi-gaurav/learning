package coursera.alg1.lect4;

public interface PriorityQueue<Key extends Comparable<Key>> {

	public abstract int size();

	public abstract boolean isEmpty();

	public abstract void insert(Key key);

}