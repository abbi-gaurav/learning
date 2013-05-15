package coursera.alg1.lect2;

public interface Queue<T> {
	boolean isEmpty();
	void enqueue(T item);
	T dequeue();
}
