package coursera.alg1.lect4;

import java.util.Arrays;
import java.util.NoSuchElementException;

import coursera.alg1.lect2.Utils;

public class IndexMinPQ<I extends Comparable<I>> {
	private final int[] pq;
	private final int[] qp;
	private I[] keys;
	private int size;
	
	@SuppressWarnings("unchecked")
	public IndexMinPQ(int size){
		this.keys = (I[]) new Comparable[size+1];
		
		this.qp = new int[size+1];
		Arrays.fill(qp, -1);
		
		this.pq = new int[size+1];
	}
	
	public void insert(int k, I item){
		validateIndex(k);
		
		size++;
		keys[k] = item;
		
		pq[size] = k;
		qp[k] = size;
		
		swim(size);
	}
	
	private void swim(int n) {
		while(n > 1 && Utils.less(keys[pq[n]], keys[pq[n/2]])){
			exch(n,n/2);
			n = n/2;
		}
	}

	private void exch(int i, int j) {
		int temp = pq[i];
		pq[i] = pq[j];
		pq[j] = temp;
		
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}
	
	public int delMin(){
		int min = pq[1];
		exch(1,size--);
		sink(1);
		
		qp[min] = -1;
		keys[pq[size+1]] = null;
		pq[size+1] = -1;
		return min;
	}
	
	public void change(int k, I item){
		validateIndex(k);
		
		keys[k] = item;
		swim(k);
		sink(k);
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public boolean contains(int k){
		validateIndex(k);
		return qp[k] != -1;
	}

	public void validateIndex(int k) {
		if (k < 0 || k >= pq.length) throw new IndexOutOfBoundsException();
	}
	
	public int minIndex(){
		return pq[1];
	}
	
	public void decreaseKey(int k, I item){
		validateIndex(k);
		
        if (!contains(k)) throw new NoSuchElementException("index is not in pq");
        if (keys[k].compareTo(item) <= 0) throw new IllegalArgumentException("Calling decreaseKey() with given argument would not strictly decrease the key");
        
		keys[k] = item;
		swim(k);
	}
	
	private void sink(int k) {
		while (2 * k <= size) {
			int j = 2 * k;

			if (j < size && Utils.less(keys[pq[j]], keys[pq[j + 1]])) {
				j++;
			}

			if (Utils.less(keys[pq[k]], keys[pq[j]])) {
				break;
			}

			exch(k, j);

			k = j;
		}
	}
}
