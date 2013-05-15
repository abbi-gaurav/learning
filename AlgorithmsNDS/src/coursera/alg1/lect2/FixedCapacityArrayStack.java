package coursera.alg1.lect2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FixedCapacityArrayStack<T> implements Stack<T>, Iterable<T>{
	private final T[] stores;
	private int n;
	@SuppressWarnings("unchecked")
	public FixedCapacityArrayStack(int size) {
		stores = (T[])new Object[size];
	}
	
	@Override
	public boolean isEmpty() {
		return n == 0;
	}

	@Override
	public void push(T item) {
		if(n == stores.length){
			throw new IndexOutOfBoundsException("cannot add beyond capacity");
		}
		stores[n++] = item;
	}

	@Override
	public T pop() {
		return isEmpty()?null:doPop();
	}

	private T doPop() {
		T item = stores[--n];
		stores[n] = null;
		return item;
	}
	
	@Override
	public T peek() {
		return isEmpty()?null:stores[n-1];
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ReverseArrayIterator();
	}
	
	private class ReverseArrayIterator implements Iterator<T>{
		private int current = n;
		@Override
		public boolean hasNext() {
			return n > 0;
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			return stores[--current];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
