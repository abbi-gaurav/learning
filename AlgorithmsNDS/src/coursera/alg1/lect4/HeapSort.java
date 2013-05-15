package coursera.alg1.lect4;

import coursera.alg1.lect2.Sort;
import coursera.alg1.lect2.Utils;

public class HeapSort<T extends Comparable<T>> implements Sort<T>{

	@Override
	public Comparable<T>[] doSort(Comparable<T>[] a) {
		int n = a.length-1;
		for(int i=n/2;i>=0;i--){
			HeapUtils.sink(a, n, i);
		}
		
		while(n > 0){
			Utils.swap(a, 0, n--);
			HeapUtils.sink(a, n, 0);
		}
		return a;
	}

}
