package coursera.alg2.week5;

import java.util.Arrays;

public class Alphabet {
	
	private final char[] alphas;
	private final int[] inverse;
	private final int r;
	private int lgR;

	public Alphabet(String alphabets) {
		validate(alphabets);
		
		this.r = alphabets.length();
		this.alphas = alphabets.toCharArray();
		
		this.inverse = new int[Character.MAX_VALUE];
		Arrays.fill(inverse, -1);
		
		for(char c=0; c<r;c++){
			inverse[alphas[c]] = c;
		}
		
		lgR = 0;
		for(int i=r-1; i >= 1; i=i/2){
			lgR++;
		}
	}
	
	public Alphabet(){
		this(256);
	}
	
	private Alphabet(int r){
		this.r = r;
		this.alphas = new char[r];
		this.inverse = new int[r];
		
		for(int i=0;i<r;i++){
			this.alphas[i] = (char)i;
			this.inverse[i] = i;
		}
	}
	
	public boolean contains(char c){
		return inverse[c] != -1;
	}
	
	public int lgR(){
		return lgR;
	}
	
	public int toIndex(char c){
		if(c < 0|| c >= inverse.length|| inverse[c] == -1){
			throw new IllegalArgumentException();
		}
		
		return inverse[c];
	}
	
	public char toChar(int index){
		if(index <0|| index >=r){
			throw new IllegalArgumentException();
		}
		
		return alphas[index];
	}
	
	public int[] toIndices(String s){
		char[] src = s.toCharArray();
		int[] indices = new int[src.length];
		
		for(int c =0;c<src.length;c++){
			indices[c] = toIndex(src[c]);
		}
		
		return indices;
	}
	
	public String toChars(int[] indices){
		StringBuilder sb = new StringBuilder();
		
		for (int i : indices) {
			sb.append(toChar(indices[i]));
		}
		
		return sb.toString();
	}
	
	private void validate(String alphabet) {
		boolean[] unicode = new boolean[Character.MAX_VALUE];
		
		for(int i=0;i<alphabet.length();i++){
			char c = alphabet.charAt(i);
			if(unicode[c]){
				throw new IllegalArgumentException("Repetable Character:"+c);
			}
			unicode[c] = true;
		}
	}
}
