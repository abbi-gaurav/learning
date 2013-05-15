package coursera.alg2.week4;

import coursera.alg1.lect2.LinkedQueue;

public class RWayTriesST<T> implements TrieBasedST<T> {

	public static final int R = 256;
	private Node root = new Node();
	
	private static class Node{
		private Object value;
		private Node[] links = new Node[R];
	}
	
	@Override
	public void put(String key, T value){
		root = put(root, key,value,0);
	}
	
	@Override
	public boolean contains(String key){
		return get(key) != null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T get(String key){
		Node x = get(root,key,0);
		if(x == null){
			return null;
		}
		
		return (T)x.value;
	}
	
	public Iterable<String> keys(){
		LinkedQueue<String> queue = new LinkedQueue<>();
		collect(root,"",queue);
		return queue;
	}
	
	public Iterable<String> keysWithPrefix(String prefix){
		Node x = get(root, prefix, 0);
		LinkedQueue<String> queue = new LinkedQueue<>();
		collect(x, prefix, queue);
		return queue;
	}
	
	public String longestPrefix(String key){
		int length = search(root,key,0,0);
		return key.substring(0, length);
	}
	
	private int search(Node x, String key, int d,int length) {
		if(x == null){
			return length;
		}
		if(x.value != null){
			length = d;
		}
		
		if(d == key.length()){
			return length;
		}
		
		char c = key.charAt(d);
		return search(x.links[c], key, d+1, length);
	}

	private void collect(Node x, String prefix, LinkedQueue<String> queue) {
		if(x == null){
			return;
		}
		if(x.value != null){
			queue.enqueue(prefix);
		}
		for(char c=0;c<R;c++){
			collect(x.links[c], prefix+c, queue);
		}
		return;
	}

	private Node get(Node x, String key, int d) {
		if(x == null){
			return null;
		}
		
		if(key.length() == d){
			return x;
		}
		char c = key.charAt(d);
		return get(x.links[c],key,d+1);
	}

	private Node put(Node x, String key, T value, int d) {
		if(x == null){
			x = new Node();
		}
		
		if(key.length() == d){
			x.value = value;
			return x;
		}
		
		char c = key.charAt(d);
		x.links[c] = put(x.links[c], key, value, d+1);
		return x;
	}
}
