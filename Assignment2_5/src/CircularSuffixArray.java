import java.util.Arrays;
import java.util.Comparator;


public class CircularSuffixArray {
	final int[] indices;

	public CircularSuffixArray(String str) {
		final char[] chars = str.toCharArray();
		
		Integer[] indexes = new Integer[chars.length];
		for(int i=0; i<indexes.length;i++){
			indexes[i] = i;
		}
		
		Arrays.sort(indexes, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				for (int i = 0; i < chars.length; i++) {
					char c1 = getCircularChar(o1, i);
					char c2 = getCircularChar(o2, i);
					if (c1 > c2) {
						return 1;
					} else if (c2 > c1) {
						return -1;
					}
				}
				return 0;
			}

			private char getCircularChar(int suffixIndex, int i) {
				if(suffixIndex+i < chars.length){
					return chars[suffixIndex+i];
				}else{
					return chars[suffixIndex+i-chars.length];
				}
			}
		});
		
		indices = new int[indexes.length];
		for(int i=0;i<indices.length;i++){
			indices[i] = indexes[i];
		}
	}
	
	
	public int length(){
		return indices.length;
	}
	
	public int index(int i){
		return indices[i];
	}
	
}
