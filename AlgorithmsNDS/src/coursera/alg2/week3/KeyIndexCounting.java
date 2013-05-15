package coursera.alg2.week3;

import java.util.Arrays;

public class KeyIndexCounting {
	
	public static  String[] keyIndexCounting(String... inputArray){
		int r = 256;
		int[] count = new int[r+1];
		
		for(int i=0;i<inputArray.length;i++){
			count[inputArray[i].charAt(0)+1]++;
		}
		
		for(int i=0;i<r;i++){
			count[i+1] += count[i]; 
		}
		
		String [] aux = new String[inputArray.length];
		
		for(int i =0;i<inputArray.length;i++){
			aux[count[inputArray[i].charAt(0)]++] = inputArray[i];
		}
		
		return aux;
	}

	
	public static void main(String[] args) {
		System.out.println(Arrays.toString(keyIndexCounting(args)));
	}
}
