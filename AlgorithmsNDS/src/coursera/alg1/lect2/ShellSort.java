package coursera.alg1.lect2;

public class ShellSort<T> implements Sort<T>{

	@Override
	public Comparable<T>[] doSort(Comparable<T>[] a) {
		int n = a.length;
		int h = 1;
		while(h < n/3){
			h = 3*h + 1;
		}
		while(h >= 1){
			for(int i = h; i < n; i++){
				for(int j = i; j >= h && Utils.less(a[j], a[j-h]); j -= h){
					Utils.swap(a, j, j-h);
				}
			}
			h = h/3;
		}
		return a;
	}
	
}
