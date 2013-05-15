package coursera.alg2.week3;

import java.util.Arrays;

public class LSD {
	public static void doSort(int w, String... a){
		int n = a.length;
		int r = 256;
		String[] aux = new String[n];
		for(int d=w-1; d>=0; d--){
			
			int count[] = new int[r+1];
			for(int i=0; i<n;i++){
				count[a[i].charAt(d)+1]++;
			}
			
			for(int i=0;i<r;i++){
				count[i+1] += count[i];
			}
			
			for(int i=0; i<n;i++){
				aux[count[a[i].charAt(d)]++] = a[i];
			}
			
			for(int i=0;i<n;i++){
				a[i] = aux[i];
			}
		}
	}
	
	public static void main(String[] args) {
		doSort(args[0].length(), args);
		System.out.println(Arrays.toString(args));
	}
}
