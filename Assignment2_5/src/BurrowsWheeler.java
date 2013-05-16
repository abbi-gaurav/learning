import java.util.ArrayList;
import java.util.List;


public class BurrowsWheeler {
	private static final int R = 256;
	
	public static void encode(){
		String s = BinaryStdIn.readString();
		CircularSuffixArray suffixArray = new CircularSuffixArray(s);
		int findZeroIndex = findZeroIndex(suffixArray);
		BinaryStdOut.write(findZeroIndex);
		
		writeLastCharOfCircularSuffixes(suffixArray, s);
		BinaryStdOut.close();
	}

	public static void decode(){
		int first = BinaryStdIn.readInt();
		char[] tArray = getTArray();
		
		//key-index counting
		int[] count = new int[R+1];
		
		for(int i=0;i<tArray.length;i++){
			count[tArray[i]+1]++;
		}
		
		for(int i=0;i<R;i++){
			count[i+1] += count[i];
		}
		int[] aux = new int[tArray.length];
		
		for(int i=0;i<tArray.length;i++){
			aux[count[tArray[i]]++] = i;
		}
		
		int current = aux[first];
		int cnt = 0;
		while (cnt++ < tArray.length) {
			BinaryStdOut.write(tArray[current]);
			current = aux[current];
		}
		BinaryStdOut.close();
	}

	

	private static char[] getTArray() {
		List<Character> charList = new ArrayList<Character>();
		while(!BinaryStdIn.isEmpty()){
			charList.add(BinaryStdIn.readChar());
		}
		char[] tArray = new char[charList.size()];
		
		for (int i = 0; i < tArray.length; i++) {
			tArray[i] = charList.get(i);
		}
		return tArray;
	}

	private static void writeLastCharOfCircularSuffixes(
			CircularSuffixArray suffixArray, String s) {
		for(int i=0; i<suffixArray.length();i++){
			int index = suffixArray.index(i);
			char c = '\0';
			if(index == 0){
				c = s.charAt(s.length()-1);
			}else{
				c = s.charAt(index-1);
			}
			BinaryStdOut.write(c);
		}
	}

	private static int findZeroIndex(CircularSuffixArray suffixArray) {
		int length = suffixArray.length();
		for(int i=0;i<length;i++){
			if(suffixArray.index(i) == 0){
				return i;
			}
		}
		throw new IllegalStateException("Invalid circular suffix");
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].equals("-")) {
				encode();
			} else if (args[0].equals("+")) {
				decode();
			} else {
				throw new IllegalArgumentException();
			}
		}
	}
}
