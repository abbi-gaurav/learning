package coursera.alg2.week5;

import coursera.alg1.lect4.BHBasedMinPrioritQ;
import coursera.alg1.lect4.MinPriorityQueue;
import edu.princeton.cs.introcs.BinaryStdIn;
import edu.princeton.cs.introcs.BinaryStdOut;

public class Huffman {
	private static final int R = 256;
	
	private static class Node implements Comparable<Node>{
		private final char ch;
		private final int freq;
		private final Node left;
		private final Node right;

		private Node(char c, int freq, Node left, Node right) {
			this.ch = c;
			this.freq = freq;
			this.left = left;
			this.right = right;
		}
		
		@Override
		public int compareTo(Node that) {
			return this.freq - that.freq;
		}
		
		private boolean isLeaf(){
			return left == null && right == null;
		}
	}

	
	public static void expand() {
		Node root = readTrie();
		int n = BinaryStdIn.readInt();
		for (int i = 0; i < n; i++) {
			Node x = root;
			while (!x.isLeaf()) {
				if (BinaryStdIn.readBoolean()) {
					x = x.right;
				} else {
					x = x.left;
				}
			}
			BinaryStdOut.write(x.ch);
		}
		
		BinaryStdOut.close();
	}
	
	public static void compress(){
		String str = BinaryStdIn.readString();
		char[] chars = str.toCharArray();
		
		int[] freq = new int[R];
		
		for(int i=0;i<chars.length;i++){
			freq[chars[i]]++;
		}
		
		Node root = buildTrie(freq);
		String[] codewords = buildCode(root);
		
		writeTrie(root);
		BinaryStdOut.write(chars.length);
		
		for(int i=0; i<chars.length;i++){
			String code = codewords[chars[i]];
			for(int j=0;j<code.length();j++){
				if(code.charAt(j) == '0'){
					BinaryStdOut.write(false);
				}else{
					BinaryStdOut.write(true);
				}
			}
		}
		
		BinaryStdOut.close();
		
	}
	
	public static void main(String[] args) {
		if(args[0].equals("-")){
			compress();
		}else if(args[0].equals("+")){
			expand();
		}else{
			System.out.println("Illegal argument");
		}
	}
	
	private static Node buildTrie(int[] freq){
		MinPriorityQueue<Node> minPQ = new BHBasedMinPrioritQ<>(freq.length);
		for(char c=0;c<freq.length;c++){
			if(freq[c] > 0){
				minPQ.insert(new Node(c, freq[c], null, null));
			}
		}

		while(minPQ.size() > 1){
			Node left = minPQ.delMin();
			Node right = minPQ.delMin();
			minPQ.insert(new Node('\0', left.freq+right.freq, left, right));
		}
		
		return minPQ.delMin();

	}
	private static Node readTrie() {
		if(BinaryStdIn.readBoolean()){
			return new Node(BinaryStdIn.readChar(),0,null,null);
		}
		return new Node('\0', 0, readTrie(), readTrie());
	}
	
	private static void writeTrie(Node x){
		if(x.isLeaf()){
			BinaryStdOut.write(true);
			BinaryStdOut.write(x.ch);
			return;
		}
		BinaryStdOut.write(false);
		writeTrie(x.left);
		writeTrie(x.right);
	}
	
	private static String[] buildCode(Node root){
		String[] st = new String[R];
		buildCode(st,root,"");
		return st;
	}
	
	private static void buildCode(String[] st, Node x, String str) {
		if(x.isLeaf()){
			st[x.ch] = str;
			return;
		}
		
		buildCode(st, x.left, str+'0');
		buildCode(st, x.right, str+'1');
	}
	
	
	
}
