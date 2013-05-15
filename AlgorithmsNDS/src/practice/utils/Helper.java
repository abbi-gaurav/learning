package practice.utils;

import java.util.Arrays;

public class Helper {

	public static int[] parseToInt(String... args) {
		int[] intArray = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			intArray[i] = Integer.parseInt(args[i]);
		}

		return intArray;
	}

	public static double[] parseToDouble(String... args) {
		double[] doubleArray = new double[args.length];
		for (int i = 0; i < args.length; i++) {
			doubleArray[i] = Double.parseDouble(args[i]);
		}

		return doubleArray;
	}

	public static String[] parseToBinaryStrings(String[] args) {
		int[] ints = parseToInt(args);
		String[] binaryStrings = new String[ints.length];
		for (int i = 0; i < ints.length; i++) {
			binaryStrings[i] = Integer.toBinaryString(ints[i]);
		}
		return binaryStrings;
	}

	public static char[] extendArrayOneWithTwo(char[] val1, char[] val2,
			char defaultChar) {
		if (val1.length >= val2.length) {
			return val1;
		}
		char[] newVal1 = new char[val2.length];
		int toIndex = val2.length - val1.length;
		Arrays.fill(newVal1, 0, toIndex, defaultChar);
		System.arraycopy(val1, 0, newVal1, toIndex, val1.length);
		return newVal1;
	}

	public static void interchange(int[] input, int i, int smallestIndex) {
		int tmp = input[i];
		input[i] = input[smallestIndex];
		input[smallestIndex] = tmp;
	}

	public static boolean compare(boolean isSecondLarger, int first, int second) {
		return isSecondLarger ? second > first : false;
	}

}
