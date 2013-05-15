package coursera.alg2.week3;
import static coursera.alg2.week3.StringUtils.charAt;

import java.util.Arrays;
public class ThreeWayRadixQuickSort {
	public static void sort(String[] a){
		sort(a,0,a.length-1,0);
	}

	private static void sort(String[] a, int lo, int hi, int d) {
		if(hi <= lo){
			return;
		}
		int lt  = lo;
		int gt = hi;
		int lv = charAt(a[lo],d);
		int i = lo+1;
		
		while(i <= gt){
			int t = charAt(a[i], d);
			
			if(t < lv){
				coursera.alg1.lect2.Utils.swap(a, i++, lt++);
			}else if(t > lv){
				coursera.alg1.lect2.Utils.swap(a, i, gt--);
			}else{
				i++;
			}
		}
		sort(a,lo,lt-1,d);
		if(lv > 0){
			sort(a,lt,gt,d+1);
		}
		sort(a,gt+1,hi,d);
	}
	
	public static void main(String[] args) {
		sort(args);
		System.out.println(Arrays.toString(args));
	}
}
