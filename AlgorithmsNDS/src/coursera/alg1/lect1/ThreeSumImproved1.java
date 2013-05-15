package coursera.alg1.lect1;

import java.util.Arrays;

import practice.utils.Helper;

public class ThreeSumImproved1 {

	public int count(int... ints) {
		Arrays.sort(ints);
		int count = 0;
		for (int i = 0; i < ints.length; i++) {
			for (int j = i + 1; j < ints.length; j++) {
				int start = j + 1;
				int binarySearch = -1;
				while ((binarySearch = Arrays.binarySearch(ints, start,
						ints.length, -(ints[i] + ints[j]))) >= 0) {
					count++;
					System.out.println(ints[i] + "::" + ints[j] + "::"
							+ ints[binarySearch]);
					start = binarySearch + 1;
				}
			}

		}
		return count;

	}

	public static void main(String[] args) {
		/* System.out.println(Arrays.toString(args)); */
		long start = System.currentTimeMillis();
		int count = new ThreeSumImproved1().count(Helper.parseToInt(args));
		long end = System.currentTimeMillis();
		System.out.println(args.length + ":: took[" + ((end - start) / 1000)
				+ "] seconds and count is[" + count + "]");
	}
}
