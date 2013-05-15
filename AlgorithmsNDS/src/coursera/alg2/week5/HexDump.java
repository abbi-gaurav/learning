package coursera.alg2.week5;

import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.StdOut;

public class HexDump {
	private static final int BYTES_PER_LINE = 16;
	public static void main(String[] args) {
		int bytesPerLine = BYTES_PER_LINE;
		if(args.length > 0){
			bytesPerLine = Integer.parseInt(args[0]);
		}
		
		int i=0;
		for(;!BinaryStdIn.isEmpty();i++){
			if(bytesPerLine == 0){
				BinaryStdIn.readChar();
				continue;
			}
			
			if(i > 0 && i%bytesPerLine == 0){
				StdOut.println();
			}
			
			char c = BinaryStdIn.readChar();
			StdOut.printf("%02x", c&0xff);
			StdOut.print(" ");
		}
		
		if(i != 0){
			StdOut.println();
		}
		
		StdOut.println(i*8+" bits");
	}
}
