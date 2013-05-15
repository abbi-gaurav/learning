package coursera.alg1.lect1;

public class RandomGenerator {
	public static void main(String[] args) {
		int count = Integer.parseInt(args[0]);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			int min = -(count) * 10;
			int max = count * 10;
			int vaue = min + (int) ((Math.random() * (max - (min))));
			sb.append(vaue + " ");
		}
		System.out.println(sb.toString().trim());
	}
}
