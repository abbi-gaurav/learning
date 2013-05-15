package coursera.alg2.week4;



public class RabinKarp {
	private static final int R = 256;
	private static final int Q = 997;
	private long patHash;
	private int rm;
	private final int m;
	
	public RabinKarp(String pattern) {
		m = pattern.length();
		rm = 1;
		
		for(int j=1; j<m;j++){
			rm = (R*rm) % Q;
		}
		
		this.patHash = StringUtils.hornerHash(pattern, m, R, Q);
	}
	
	public int search(String txt){
		int n = txt.length();
		
		long txtHash = StringUtils.hornerHash(txt, m, R, Q);
		if(txtHash == patHash){
			return 0;
		}
		
		for(int i =m; i<=n-m;i++){
			txtHash = (txtHash + Q - (rm*txt.charAt(i-m))%Q) % Q;
			txtHash = (txtHash * R + txt.charAt(i)) % Q;
			if(patHash == txtHash){
				return i-m+1;
			}
		}
		return -1;
	}
}
