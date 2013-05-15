package coursera.alg1.lect2;

public interface Stack<T> extends Iterable<T> {

	public abstract boolean isEmpty();

	public abstract void push(T item);

	public abstract T pop();
	
	T peek();

}