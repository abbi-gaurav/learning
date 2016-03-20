package codingInterviews;

public class BitOperations {
	public static boolean getBit(int number,int index){
		int leftShifted = getLeftShitedOne(index);
		int andOpRes = number & leftShifted;
		int rightShifted = andOpRes >> (index-1);
		
		return rightShifted == 1;
	}
	
	public static int setBit(int number, int index, boolean set){
		if(set == getBit(number, index)) return number;
		
		int leftShifted = getLeftShitedOne(index);
		
		return number ^ leftShifted;
	}

	private static int getLeftShitedOne(int index) {
		return 1 << (index-1);
	}
	
	public static void main(String[] args) {
		System.out.println(getBit(3, 3));
		System.out.println(setBit(6, 2, true));
	}
}
