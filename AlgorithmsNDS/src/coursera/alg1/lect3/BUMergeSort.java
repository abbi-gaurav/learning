package coursera.alg1.lect3;

import coursera.alg1.lect2.Sort;

public class BUMergeSort<Item> extends MergeSort<Item> implements Sort<Item>{

	@SuppressWarnings("unchecked")
	@Override
	public Comparable<Item>[] doSort(Comparable<Item>[] a) {
		int n = a.length;
		Comparable<Item>[] aux = new Comparable[n];
		for(int sz = 1; sz < n; sz *= 2){
			for(int lo = 0; lo < n-sz; lo += 2*sz){
				merge(a,  aux, lo, lo+sz-1, Math.min(lo+sz+sz-1,n-1));
			}
		}
		return a;
	}

}
