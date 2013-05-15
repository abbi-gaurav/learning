package coursera.alg1.lect4;

import edu.princeton.cs.introcs.StdRandom;

public class TestHelper {

	public static int[] getShuffledArray(int length) {
		int[] array = getIntArray(length);
		StdRandom.shuffle(array);
		return array;
	}
	

	public static int[] getIntArray(int size){
		int[] array = new int[size];
		for(int i =0; i < size;i++){
			array[i] = i;
		}
		return array;
	}


	public static Double[] getArray(int count) {
		Double[] randoms = new Double[count];
		for(int i = 0;i<count;i++){
			randoms[i] = StdRandom.uniform();
		}
		return randoms;
	}
}
