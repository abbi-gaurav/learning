package coursera.alg1.lect4;

public interface SymbolTable<K extends Comparable<K>, V> extends Iterable<K>{

	V get(K key);

	void put(K key, V value);
	
	K min();
	
	K max();
	
	/**
	 * Largest key smaller than specified key
	 */
	K floor(K key);
	
	/**
	 * smallest key larger than the specified
	 */
	K ceiling(K key);
	
	int size();
	
	/**
	 * @param key
	 * @return count of keys less than this key
	 */
	int rank(K key);
	
	void deleteMin();
	
	void deleteMax();
	
	void delete(K key);
	
	K rankBasedSelection(int n);
	
	boolean contains(K key);
}