package coursera.alg2.week4;

public class StringUtils {
	public static long hornerHash(String key, int m, int radix, int q){
		long h = 0;
		for(int i=0;i<m;i++){
			h = (radix*h+key.charAt(i))%q;
		}
		return h;
	}
	
	public static void main(String[] args) {
		System.out.println(10000%997);
	}
}
