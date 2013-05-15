package coursera.alg2.week4;


public class KMP {
	private static final int R = 256;
	private final int m;
	private final int[][] dfa;

	public KMP(String pattern) {
		m = pattern.length();
		dfa = new int[R][m];
		
		dfa[pattern.charAt(0)][0] = 1;
		for(int x=0,j=1;j<m;j++){
			for(char c=0; c<R;c++){
				//set up no match case
				dfa[c][j] = dfa[c][x];
			}
			//set up match case for each column
			dfa[pattern.charAt(j)][j] = j+1;
			
			//update x based on backup
			x = dfa[pattern.charAt(j)][x];
		}
	}
	
	public int search(String txt){
		int i,j;
		int n = txt.length();
		
		for(i=0,j=0; i<n && j<m;i++){
			j = dfa[txt.charAt(i)][j];
		}
		
		if(j == m){
			return i-m;
		}else{
			return -1;
		}
	}
}
