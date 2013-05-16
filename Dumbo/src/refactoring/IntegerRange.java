package refactoring;

public class IntegerRange {
	public static void main(String[] args) {
		int i = Integer.parseInt(args[0]);
		legacyWay(i);
		refactoredWay(i);
	}

	private static void refactoredWay(int i) {
		Range range = new Range(16,34);
		System.out.println(range.contains(i));
	}

	public static void legacyWay(int i) {
		int rangeStart = 16;
		int rangeEnd = 34;
		if(i >= rangeStart && i< rangeEnd){
			System.out.println(i+"is within range");
		}
	}
}
