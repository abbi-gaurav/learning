package coursera.alg2.week4;

public interface TrieBasedST<T> {

	void put(String key, T value);

	boolean contains(String key);

	T get(String key);

}