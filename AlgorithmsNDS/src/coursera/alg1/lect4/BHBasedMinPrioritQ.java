package coursera.alg1.lect4;

import java.util.Arrays;

import coursera.alg1.lect2.Utils;

public class BHBasedMinPrioritQ<Key extends Comparable<Key>> implements MinPriorityQueue<Key>{
	private int size;
	private Key[] store;
	
	@SuppressWarnings("unchecked")
	public BHBasedMinPrioritQ(int initCapacity) {
		this.store = (Key[]) new Comparable[initCapacity+1];
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
		if(size+1 == store.length){
			resize(store.length * 2);
		}
		this.store[++size] = key;
		swim(size);
	}

	private void swim(int n) {
		while(n > 1 && Utils.less(store[n], store[n/2])){
			Utils.swap(store, n, n/2);
			n = n/2;
		}
	}

	@Override
	public Key delMin() {
		if(size == store.length/4){
			resize(store.length/2);
		}
		Key min = store[1];
		Utils.swap(store, 1, size);
		sink(1,--size);
		store[size+1] = null;
		return min;
	}

	private void sink(int k, int n) {
		while(2*k <=n){
			int j = 2*k;
			if(j<n && Utils.less(store[j+1], store[j])) j++;
			
			if(Utils.less(store[k], store[j]))	break;
			
			Utils.swap(store, k, j);
			
			k = j;
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(store);
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int newSize) {
		Key[] newStore = (Key[])new Comparable[newSize];
		for(int i =1; i <=size;i++){
			newStore[i] = store[i];
		}
		this.store = newStore;
	}

}
