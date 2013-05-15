package coursera.alg1.lect3;

import coursera.alg1.lect2.Sort;
import coursera.alg1.lect2.Utils;

@SuppressWarnings("unchecked")
public class MergeSort<T> implements Sort<T> {

	@Override
	public Comparable<T>[] doSort(Comparable<T>[] a) {
		Comparable<T>[] aux = new Comparable[a.length];
		doSort(a,aux,0,a.length-1);
		return a;
	}

	private void doSort(Comparable<T>[] a, Comparable<T>[] aux, int lo, int hi) {
		if(hi <= lo){
			return;
		}
		int mid = lo + (hi-lo)/2;
		//TODO: insertion sort for small sizes
		doSort(a,aux,lo,mid);
		doSort(a,aux,mid+1,hi);
		
		if(!Utils.less(a[mid], a[mid+1])){
			merge(a,aux,lo,mid,hi);
		}
	}

	protected final void merge(Comparable<T>[] a, Comparable<T>[] aux, int lo, int mid,
			int hi) {
		for(int k = lo; k <= hi;k++){
			aux[k] = a[k];
		}
		
		int i = lo;
		int j = mid+1;
		
		for(int k = lo; k <= hi; k++){
			if(i > mid){
				a[k] = aux[j++];
			}else if (j > hi){
				a[k] = aux[i++];
			}else if(Utils.less(aux[j], aux[i])){
				a[k] = aux[j++];
			}else {
				a[k] = aux[i++];
			}
		}
		return;
	}

}
