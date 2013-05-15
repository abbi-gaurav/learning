package coursera.alg2.week3;

import java.util.Arrays;

public class MSD {
	public static void doSort(String... a){
		String[] aux = new String[a.length];
		doSort(a,aux,0,a.length-1,0);
	}

	private static void doSort(String[] a, String[] aux, int lo, int hi,
			int d) {
		if(hi <= lo){
			return;
		}
		int r = 256;
		int[] count = new int[r+2];
		
		for(int i=lo; i <= hi;i++){
			count[StringUtils.charAt(a[i], d)+2]++;
		}
		
		for(int i=0;i<r+1;i++){
			count[i+1] += count[i];
		}
		
		for(int i=lo;i<=hi;i++){
			aux[count[StringUtils.charAt(a[i], d)+1]++] = a[i];
		}
		
		for(int i=lo;i<= hi;i++){
			a[i] = aux[i-lo];
		}
		
		for(int i=0;i<r;i++){
			doSort(a, aux, lo+count[i], lo+count[i+1]-1, d+1);
		}
	}
	
	public static void main(String[] args) {
		doSort(args);
		System.out.println(Arrays.toString(args));
	}
}
