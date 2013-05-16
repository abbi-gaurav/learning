package refactoring;

public class Range {
	private final int higher;
	private final int lower;

	public Range(int lower, int higher) {
		this.lower = lower;
		this.higher = higher;
	}
	
	public boolean contains(int number){
		return (number >= lower && number < higher);
	}
}
