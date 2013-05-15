package coursera.alg1.lect1;

import practice.utils.Helper;

public class ThreeSum {

	public int count(int... ints) {
		int count = 0;
		for (int i = 0; i < ints.length; i++) {
			for (int j = i + 1; j < ints.length; j++) {
				for (int k = j + 1; k < ints.length; k++) {
					if (ints[i] + ints[j] + ints[k] == 0) {
						System.out.println(ints[i] + "::" + ints[j] + "::"
								+ ints[k]);
						count++;
					}
				}
			}

		}
		return count;

	}

	public static void main(String[] args) {
		/* System.out.println(Arrays.toString(args)); */
		long start = System.currentTimeMillis();
		int count = new ThreeSum().count(Helper.parseToInt(args));
		long end = System.currentTimeMillis();
		System.out.println(args.length + ":: took[" + ((end - start) / 1000)
				+ "] seconds and count is[" + count + "]");
	}
}
