package learn.algos;

import java.util.Arrays;
import java.util.Random;

public class BubbleSort {
	private static final Random random = new Random();
	private static final int SIZE = 10;
	private static final int LIMIT = 250;
	
	public static void main(String[] args) {
		int[] arr = getdata();
		System.out.println("unsorted array is:["+printArr(arr)+"]");
		arr = sort1(arr);
		System.out.println("sorted arrays is:["+printArr(arr)+"]");
	}

	private static String printArr(int[] arr) {
		return Arrays.toString(arr);
	}
	
	public static int[] getdata(){
		int arr[] = new int[SIZE];
		for(int i =0;i<SIZE;i++){
			arr[i] = random.nextInt(LIMIT);
		}
		
		return arr;
	}
	
	public static int[] sort1(int[] arr){
		boolean swapped = true;
		while(swapped){
			swapped = false;
			for(int i = 0;i<arr.length-1;i++){
				if(arr[i] > arr[i+1]){
					int tmp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = tmp;
					swapped = true;
				}
			}
		}
		
		return arr;
	}
}
