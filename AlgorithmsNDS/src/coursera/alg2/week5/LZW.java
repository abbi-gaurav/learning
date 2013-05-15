package coursera.alg2.week5;

import coursera.alg2.week4.TernarySearchTriesST;
import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.BinaryStdOut;

public class LZW {
	private static final int R = 256;
	private static final int W = 8;
	private static final int L = 4096;

	public static void compress(){
		String input = BinaryStdIn.readString();
		
		TernarySearchTriesST<Integer> tst = createBaseTable();
		
		int code = R+1;
		
		while(input.length()>0){
			String lp = tst.longestPrefix(input);
			BinaryStdOut.write(tst.get(lp), W);
			int lpt = lp.length();
			
			if(lpt<input.length() && code < L){
				tst.put(input.substring(0, lpt+1), code++);
			}
			
			input = input.substring(lpt);
		}
		
		BinaryStdOut.write(R,W);
		BinaryStdOut.close();
	}
	
	public static void expand() {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++){
            st[i] = "" + (char) i;
        }
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R){
            	break;
            }
            String s = st[codeword];
            if (i == codeword){
            	s = val + val.charAt(0);   // special case hack
            }
            if (i < L){
            	st[i++] = val + s.charAt(0);
            }
            val = s;
        }
        BinaryStdOut.close();
    }

	private static TernarySearchTriesST<Integer> createBaseTable() {
		TernarySearchTriesST<Integer> tst = new TernarySearchTriesST<>();
		
		for(int i=0; i<R;i++){
			tst.put(""+(char)i, i);
		}
		return tst;
	}
	
	public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
