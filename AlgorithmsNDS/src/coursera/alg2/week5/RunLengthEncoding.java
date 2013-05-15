package coursera.alg2.week5;

import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.BinaryStdOut;

public class RunLengthEncoding {
	private static final int lgR = 8;
	private static final int R = 256;
	
	public static void compress(){
		boolean state = false;
		char count = 0;
		boolean read = false;
		
		while(!BinaryStdIn.isEmpty()){
			read = BinaryStdIn.readBoolean();
			
			if(read != state){
				writeCount(count);
				state = !state;
				count = 0;
			}else{
				if(count == 255){
					writeCount(count);
					count = 0;
					writeCount(count);
				}
			}
			count++;
		}
		BinaryStdOut.write(count);
		
		BinaryStdOut.close();
	}

	private static void writeCount(char count) {
		BinaryStdOut.write(count);
	}
	
	public static void expand(){
		boolean state = false;
		while (!BinaryStdIn.isEmpty()) {
			int count = BinaryStdIn.readInt(lgR);
			for (int i = 0; i < count; i++) {
				BinaryStdOut.write(state);
			}
			state = !state;
		}
		
		BinaryStdOut.close();
	}
	
	public static void main(String[] args) {
		if(args[0].equals("+")){
			expand();
		}else if(args[0].equals("-")){
			compress();
		}else{
			System.err.println("Usage Java RunLengthEncoding <+/-> <Input>");
		}
	}
}
