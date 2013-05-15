package practice.chapter2;

import java.util.Arrays;

import practice.utils.Helper;

public class InstertionSort {
	public static void main(String[] args) {
		int[] input = Helper.parseToInt(args);
		int[] increasing = doInsertSort(true, input);
		System.out.println(Arrays.toString(increasing));

		int[] decreasing = doInsertSort(false, input);
		System.out.println(Arrays.toString(decreasing));
	}

	private static int[] doInsertSort(boolean increasing, int... numbers) {
		for (int j = 1; j < numbers.length; j++) {
			int key = numbers[j];

			int i = (j - 1);
			for (; i >= 0 && Helper.compare(increasing, key, numbers[i]); i--) {
				numbers[i + 1] = numbers[i];
			}
			numbers[i + 1] = key;
		}

		return numbers;
	}

}
