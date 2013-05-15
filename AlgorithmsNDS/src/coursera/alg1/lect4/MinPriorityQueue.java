package coursera.alg1.lect4;

public interface MinPriorityQueue<Key extends Comparable<Key>> extends PriorityQueue<Key>{

	Key delMin();
}
