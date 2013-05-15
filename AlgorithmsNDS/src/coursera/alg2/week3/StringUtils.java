package coursera.alg2.week3;

public class StringUtils {

	public static int charAt(String str, int d){
		if(d < str.length()){
			return str.charAt(d);
		}
		
		return -1;
	}
	
	public static int longestCommonPrefix(String x, String y){
		int n = Math.min(x.length(), y.length());
		
		for(int i=0;i<n;i++){
			if(x.charAt(i) != y.charAt(i)){
				return i;
			}
		}
		
		return n;
	}

}
