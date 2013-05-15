package coursera.alg1.lect2;

@SuppressWarnings("unchecked")
public class Utils {
	/**
	 * @param x
	 * @param y
	 * @return true if x < y
	 */
	public static <T>boolean less( Comparable<T> x, Comparable<T> y){
		return x.compareTo( (T) y) < 0;
	}
	
	public static <T>  void swap(T[]a,int i,int j){
		T temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
