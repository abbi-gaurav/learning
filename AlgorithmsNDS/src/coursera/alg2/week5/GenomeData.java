package coursera.alg2.week5;

import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.BinaryStdOut;
import edu.princeton.cs.introcs.StdOut;

public class GenomeData {
	private static final Alphabet DNA = new Alphabet("ACTG");
	
	public static void compress(){
		String s = BinaryStdIn.readString();
		
		int n = s.length();
		
		BinaryStdOut.write(n);
		
		for(int i=0;i<n;i++){
			int index = DNA.toIndex(s.charAt(i));
			BinaryStdOut.write(index, DNA.lgR());
		}
		
		BinaryStdOut.close();
	}
	
	public static  void expand(){
		int n = BinaryStdIn.readInt();
		
		int w = DNA.lgR();
		
		for(int i=0;i<n;i++){
			char c = BinaryStdIn.readChar(w);
			BinaryStdOut.write(DNA.toChar(c));
		}
		BinaryStdOut.close();
		StdOut.println();
		StdOut.println();
	}
	
	public static void main(String[] args) {
		if (args[0].equals("-")) compress();
		if (args[0].equals("+")) expand();
	}
}
