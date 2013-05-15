package coursera.alg1.lect4;

import java.util.Arrays;

import coursera.alg1.lect2.Utils;

public class UnorderedMaxPQ<Key extends Comparable<Key>> implements MaxPriorityQueue<Key>{
	
	private Key[] store;
	private int size;
	
	@SuppressWarnings("unchecked")
	public UnorderedMaxPQ(int capacity) {
		this.store = (Key[])new Comparable[capacity];
	}
	
	@Override
	public Key delMax() {
		int max = 0;
		for(int i = 1; i < size; i++){
			if(Utils.less(store[max], store[i])){
				max = i;
			}
		}
		Utils.swap(store, max, size-1);
		Key key = store[size-1];
		store[--size] = null;
		return key;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void insert(Key key) {
		throwIfSizeExceeded();
		store[size++] = key;
	}

	private void throwIfSizeExceeded() {
		if(size == store.length){
			throw new IndexOutOfBoundsException("Cannot add beyond max size");
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(store);
	}

}
