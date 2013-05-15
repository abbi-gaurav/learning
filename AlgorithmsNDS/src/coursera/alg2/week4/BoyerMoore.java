package coursera.alg2.week4;

import java.util.Arrays;

public class BoyerMoore {
	private static final int R = 256;
	private final int[] right;
	private final int m;
	private final String pattern;

	public BoyerMoore(String pattern){
		this.right = new int[R];
		this.pattern = pattern;
		m = pattern.length();
		
		Arrays.fill(right, -1);
		
		for(int j=0;j<m;j++){
			right[pattern.charAt(j)] = j;
		}
	}
	
	public int search(String txt){
		int skip = 0;
		int n = txt.length();
		for(int i=0;i<=n-m;i+=skip){
			skip=0;
			for(int j=m-1;j>=0;j--){
				if(pattern.charAt(j) != txt.charAt(i+j)){
					skip = Math.max(1, j-right[txt.charAt(i+j)]);
					break;
				}
			}
			if(skip == 0){
				return i;
			}
		}
		return -1;
	}
}
