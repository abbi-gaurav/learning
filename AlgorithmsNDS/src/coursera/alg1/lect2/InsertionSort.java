package coursera.alg1.lect2;

public class InsertionSort<T> implements Sort<T>{

	@Override
	public Comparable<T>[] doSort(Comparable<T>[] a) {
		for(int i = 0; i< a.length;i++){
			for(int j = i;j>0;j-- ){
				if(Utils.less(a[j], a[j-1])){
					Utils.swap(a, j, j-1);
				}else{
					break;
				}
			}
		}
		return a;
	}

}
