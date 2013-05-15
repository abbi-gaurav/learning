package coursera.alg2.week3;

import java.util.Arrays;

public class LRS {
	public static String find(String s){
		int n = s.length();
		String[] suffixes = new String[n];
		
		for(int i=0;i<n;i++){
			suffixes[i] = s.substring(i, n);
		}
		Arrays.sort(suffixes);
		String lrs = "";
		for(int i=0;i<n-1;i++){
			int len = StringUtils.longestCommonPrefix(suffixes[i], suffixes[i+1]);
			
			if(len > lrs.length()){
				lrs = suffixes[i].substring(0, len);
			}
		}
		return lrs;
	}
	
	public static void main(String[] args) {
		System.out.println(args[0].length());
		System.out.println(find(args[0]));
	}
}
