package coursera.alg1.lect3;
import coursera.alg1.lect2.Utils;


@SuppressWarnings("unchecked")
public class ThreeWayQuickSort<T> extends QuickSort<T>{
	
	@Override
	protected void sort(Comparable<T>[] a, int lo, int hi) {
		if(hi <= lo) return;
		
		int lt = lo;
		int gt = hi;
		int i = lo;
		Comparable<T> v = a[lo];
		
		while(i <= gt){
			int cmp = a[i].compareTo((T) v);
			if(cmp < 0){
				Utils.swap(a, i++, lt++);
			}else if(cmp > 0){
				Utils.swap(a, i, gt--);
			}else{
				i++;
			}
		}
		
		sort(a,lo, lt-1);
		sort(a, gt+1, hi);
	}

}
