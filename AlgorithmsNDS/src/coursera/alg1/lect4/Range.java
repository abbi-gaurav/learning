package coursera.alg1.lect4;


public interface Range<K extends Comparable<K>> {
	/**
	 * @param lo
	 * @param high
	 * @return range lo < num <= hi
	 */
	int rangeCount(K lo, K high);
	
	/**
	 * @param lo
	 * @param high
	 * @return (lo, high]
	 */
	Iterable<K> range (K lo, K high);
}
