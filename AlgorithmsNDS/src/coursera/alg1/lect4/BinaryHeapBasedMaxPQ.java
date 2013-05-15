package coursera.alg1.lect4;

import java.util.Arrays;

import coursera.alg1.lect2.Utils;

public class BinaryHeapBasedMaxPQ<Key extends Comparable<Key>> implements MaxPriorityQueue<Key>{
	
	private final Key[] store;
	private int size;
	
	@SuppressWarnings("unchecked")
	public BinaryHeapBasedMaxPQ(int capacity) {
		this.store = (Key[]) new Comparable[capacity+1];
	}
	
	@Override
	public Key delMax() {
		Key max = store[1];
		Utils.swap(store, 1, size--);
		coursera.alg1.lect4.HeapUtils.sink(store,size,1);
		store[size+1] = null;
		return max;
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
		store[++size] = key;
		swim(size);
	}

	private void swim(int n) {
		while(n > 1 && Utils.less(store[n/2], store[n])){
			Utils.swap(store, n, n/2);
			n = n/2;
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(store);
	}
}
