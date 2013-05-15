package coursera.alg2.week4;

public class TernarySearchTriesST<T> implements TrieBasedST<T>{
	private class Node{
		private final char c;
		private T value;
		private Node left,middle,right;
		
		public Node(char c) {
			this.c = c;
		}
	}
	
	private Node root; 
	
	@Override
	public void put(String key, T value){
		root = put(root,key,value,0);
	}
	
	@Override
	public T get(String key){
		Node x = get(key,root,0);
		
		if(x == null){
			return null;
		}
		
		return x.value;
	}
	
	@Override
	public boolean contains(String key){
		return get(key) != null;
	}
	
	private Node get(String key, Node x, int d) {
		if(x == null){
			return null;
		}
		
		char charAtD = key.charAt(d);
		
		if(x.c == charAtD){
			if(d == key.length()-1){
				return x;
			}else{
				return get(key,x.middle,d+1);
			}
		}else if(x.c>charAtD){
			return get(key,x.left,d);
		}else{
			return get(key, x.right, d);
		}
		
	}

	private Node put(Node x, String key, T value, int d) {
		char charAtD = key.charAt(d);
		if(x == null){
			x = new Node(charAtD);
		}


		if(x.c == charAtD){
			if(d == key.length()-1){
				x.value = value;
				return x;
			}else{
				x.middle = put(x.middle,key,value,d+1);
			}
		}else if(x.c>charAtD){
			x.left = put(x.left,key,value,d);
		}else{
			x.right = put(x.right,key,value,d);
		}
		return x;
	}
	public String longestPrefix(String key){
		int length = search(root,key,0,0);
		return key.substring(0, length);
	}
	
	private int search(Node x, String key, int d, int length) {
		if(x == null){
			return length;
		}
		if(x.value != null){
			length = d;
		}
		if(d == key.length()){
			return d;
		}
		
		char charAtD = key.charAt(d);
		if(x.c == charAtD){
			return search(x.middle, key, d+1, length);
		}else if(x.c > charAtD){
			return search(x.left, key, d, length);
		}else{
			return search(x.right, key, d, length);
		}
	}

	public static void main(String[] args) {
		TernarySearchTriesST<Integer> st = new TernarySearchTriesST<>();
		int i=0;
		for(String arg:args){
			st.put(arg, i++);
		}
	}
}
