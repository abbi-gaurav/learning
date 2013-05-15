package practice.chapter2;

import java.util.Arrays;

import practice.utils.Helper;

public class SelectionSort {
	public static void main(String[] args) {
		int[] input = Helper.parseToInt(args);
		System.out.println(Arrays.toString(doSort(input, true)));
	}

	private static int[] doSort(int[] input, boolean increasing) {
		for (int i = 0; i < input.length - 1; i++) {
			int smallestIndex = i;
			for (int j = i + 1; j < input.length; j++) {
				if (Helper.compare(increasing, input[j], input[smallestIndex])) {
					smallestIndex = j;
				}
			}
			if (smallestIndex != i) {
				Helper.interchange(input, i, smallestIndex);
			}
		}
		return input;
	}
}
