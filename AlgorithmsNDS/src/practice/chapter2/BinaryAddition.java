package practice.chapter2;

import practice.utils.Helper;

public class BinaryAddition {
	public static void main(String[] args) {
		String[] inputBinary = Helper.parseToBinaryStrings(args);
		System.out.println(Integer.parseInt(performBinaryAddition(inputBinary),
				2));
	}

	private static String performBinaryAddition(String... inputBinary) {
		char[] val1 = inputBinary[0].toCharArray();
		char[] val2 = inputBinary[1].toCharArray();

		val1 = Helper.extendArrayOneWithTwo(val1, val2, '0');
		val2 = Helper.extendArrayOneWithTwo(val2, val1, '0');

		char[] totalSum = new char[val1.length + 1];

		char carryOver = '0';
		for (int j = val1.length - 1; j >= 0; j--) {
			BitSum sum = binarySum(val1[j], val2[j], carryOver);
			totalSum[j + 1] = sum.bit;
			carryOver = sum.carryOver;
		}
		totalSum[0] = carryOver;

		for (int i = 2; i < inputBinary.length; i++) {
			totalSum = performBinaryAddition(new String(totalSum),
					inputBinary[i]).toCharArray();
		}

		return new String(totalSum);
	}

	private static BitSum binarySum(char c1, char c2, char carryOver) {
		if (c1 == '1' && c2 == '1') {
			return new BitSum(carryOver, '1');
		} else if (c1 == '0' && c2 == '0') {
			return new BitSum(carryOver, '0');
		}
		if (carryOver == '1') {
			return new BitSum('0', '1');
		}
		return new BitSum('1', '0');
	}

	private static class BitSum {
		final char bit;
		final char carryOver;

		public BitSum(char bit, char carryOver) {
			this.bit = bit;
			this.carryOver = carryOver;
		}
	}
}
