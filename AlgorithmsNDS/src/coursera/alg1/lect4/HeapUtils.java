package coursera.alg1.lect4;

import coursera.alg1.lect2.Utils;

public class HeapUtils {

	public static <K extends Comparable<K>> void sink(Comparable<K>[] array, int n, int k) {
		while(2*k <= n ){
			int j = 2*k;
			
			if(j < n && Utils.less(array[j], array[j+1]))	j++;
			
			if(!Utils.less(array[k], array[j]))	break;
			
			Utils.swap(array, k, j);
			
			k = j;
		}
	}

}
