package coursera.alg1.lect2;

public class SelectionSort<T> implements Sort<T> {
	
	@Override
	public Comparable<T>[] doSort(Comparable<T>[] a){
		int length = a.length;
		for(int i = 0; i < length;i++){
			int min = i;
			for(int j = i+1; j < length;j++){
				if(Utils.less(a[j], a[min])){
					min = j;
				}
			}
			Utils.swap(a,i,min);
		}
		return a;
	}
}
