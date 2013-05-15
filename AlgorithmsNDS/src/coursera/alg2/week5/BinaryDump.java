package coursera.alg2.week5;

import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.StdOut;

public class BinaryDump {
	public static void main(String[] args) {
		int width = Integer.parseInt(args[0]);
		
		int count;
		
		for(count = 0; !BinaryStdIn.isEmpty();count++){
			if(width == 0){
				return;
			}
			
			if(count !=0 && count % width == 0){
				StdOut.println();
			}
			
			boolean b = BinaryStdIn.readBoolean();
			if(b == false){
				StdOut.printf("0");
			}else{
				StdOut.printf("1");
			}
		}
		
		StdOut.println();
		StdOut.println(count+" bits");
	}
}
