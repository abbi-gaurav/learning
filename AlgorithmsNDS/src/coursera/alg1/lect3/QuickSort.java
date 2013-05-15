package coursera.alg1.lect3;


import coursera.alg1.lect2.Sort;
import coursera.alg1.lect2.Utils;
import edu.princeton.cs.introcs.StdRandom;

public class QuickSort<T> implements Sort<T>, Select<T>{

	@Override
	public Comparable<T>[] doSort(Comparable<T>[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length-1);
		return null;
	}
	
	protected void sort(Comparable<T>[] a, int lo, int hi){
		if(hi <= lo) return;
		
		int j = partition(a, lo, hi);
		sort(a, lo, j-1);
		sort(a,j+1,hi);
	}
	
	private int partition(Comparable<T>[] a, int lo, int hi){
		int i = lo;
		int j = hi+1;
		while(true){
			while(Utils.less(a[++i], a[lo])){
				if(i == hi) break;
			}

			while(Utils.less(a[lo], a[--j])){
				if(j == lo) break;
			}

			if(i >= j) break;
			
			Utils.swap(a, i, j);
		}
		
		Utils.swap(a, lo, j);
		return j;
	}

	@Override
	public Comparable<T> select(Comparable<T>[] a, int k) {
		throwIfInvalid(a,k);
		StdRandom.shuffle(a);
		int lo = 0;
		int hi = a.length-1;
		while(hi > lo){
			int j = partition(a, lo, hi);
			if(j < k){
				lo = j+1;
			}else if(j > k){
				hi = j-1;
			}else{
				return a[k];
			}
		}
		return a[k];
	}

	private void throwIfInvalid(Comparable<T>[] a, int k) {
		if(a == null) throw new NullPointerException("Array is null");
		
		if(k < 1 || k > a.length) throw new IllegalArgumentException("k is out of bounds");
	}
	
	
}
