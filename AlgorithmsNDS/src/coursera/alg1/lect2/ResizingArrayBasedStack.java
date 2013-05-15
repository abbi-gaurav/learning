package coursera.alg1.lect2;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class ResizingArrayBasedStack<T> implements Stack<T> {
	
	private T[] store;
	private int n;

	public ResizingArrayBasedStack() {
		this.store = (T[])new Object[1];
		this.n = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return n == 0;
	}

	@Override
	public void push(T item) {
		if(n == store.length){
			resize(store.length*2);
		}
		store[n++] = item;
	}

	private void resize(int newSize) {
		T[] newStore = (T[])new Object[newSize];
		for(int i =0; i <n;i++){
			newStore[i] = store[i];
		}
		this.store = newStore;
	}

	@Override
	public T pop() {
		if(n <= 0){
			return null;
		}
		if(n == store.length/4 ){
			resize(store.length/2);
		}
		T item = store[--n];
		store[n] = null;
		return item;
	}
	
	@Override
	public T peek() {
		return isEmpty() ? null :store[n-1];
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ReverseArrayIterator();
	}
	
	private class ReverseArrayIterator implements Iterator<T>{
		private int current = n;
		@Override
		public boolean hasNext() {
			return current > 0;
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			return store[--current];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
