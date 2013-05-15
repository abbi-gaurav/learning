package coursera.alg1.lect4;

public interface MaxPriorityQueue<Key extends Comparable<Key>> extends PriorityQueue<Key> {
	Key delMax();
}
