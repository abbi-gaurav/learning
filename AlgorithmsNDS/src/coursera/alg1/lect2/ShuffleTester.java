package coursera.alg1.lect2;
import java.util.Arrays;



public class ShuffleTester {
	public static void main(String[] args) {
		testShuffle(args,new KnuthShuffle<Integer>());
	}

	private static void testShuffle(String[] args,
			Shuffle<Integer> shuffle) {
		Integer[] initArray = new Integer[10];
		for(int i = 0; i<10;i++){
			initArray[i] = i;
		}
		System.out.println(Arrays.toString(initArray));
		
		shuffle.shuffle(initArray);
		
		System.out.println(Arrays.toString(initArray));
	}
}
