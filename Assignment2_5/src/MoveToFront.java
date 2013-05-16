import java.util.LinkedList;


public class MoveToFront {
	private static final int R = 256;
	
	public static void encode(){
		LinkedList<Character> chars = initASCII();
		
		while(!BinaryStdIn.isEmpty()){
			char c = BinaryStdIn.readChar();
			int index = chars.indexOf(c);
			BinaryStdOut.write(index,8);
			chars.remove(index);
			chars.addFirst(c);
		}
		BinaryStdOut.close();
		
	}
	
	public static void decode(){
		LinkedList<Character> list = initASCII();
		while(!BinaryStdIn.isEmpty()){
			int index = BinaryStdIn.readChar();
			char c = list.remove(index);
			BinaryStdOut.write(c);
			list.addFirst(c);
		}
		BinaryStdOut.close();
	}
	
	private static LinkedList<Character> initASCII() {
		LinkedList<Character> list = new LinkedList<Character>();
		for(char c = 0; c<R;c++){
			list.add(c, c);
		}
		
		return list;
	}
	
	public static void main(String[] args) {
		if(args[0].equals("-")){
			encode();
		}else if(args[0].equals("+")){
			decode();
		}else{
			throw new IllegalArgumentException();
		}
	}
}
