package coursera.alg1.lect5;

import com.sun.xml.internal.txw2.IllegalSignatureException;

public class LeftLeaningRedBlackBST<K extends Comparable<K>, V> {
	private Node root;
	private final class Node{
		final K key;
		V value;
		Node left;
		Node right;
		Color color;
		
		public Node(K key, V value, Color color) {
			this.key = key;
			this.value = value;
			this.color = color;
		}
	}
	
	private boolean isRed(Node x){
		if(x == null){
			return false;
		}
		
		return  x.color == Color.RED;
	}

	private enum Color{
		RED, BLACK
	}
	
	private Node rotateLeft(Node h){
		if (h.right.color != Color.RED) {
			throw new IllegalSignatureException(
					"Tree mechanics are wrong, rl is called when not required");
		}
		
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = Color.RED;
		return x;
	}
	
	private Node rotateRight(Node h){
		if(h.left.color != Color.RED){
			throw new IllegalSignatureException(
					"Tree mechanics are wrong, rr is called when not required");
		}
		Node x = h.left;
		h.left = x.right;
		x.right=h;
		x.color = h.color;
		h.color = Color.RED;
		return x;
	}
	
	private void flipColor(Node h){
		if (h.left.color == Color.BLACK || h.right.color == Color.BLACK
				|| h.color == Color.RED) {
			throw new IllegalSignatureException(
					"Tree mechanics are wrong, flip is called when not required");
		}
		h.color = Color.RED;
		h.left.color = Color.BLACK;
		h.right.color = Color.BLACK;
	}
	
	public void put(K key, V value) {
		root = this.put(root, key, value);
	}

	private Node put(Node h, K key, V value) {
		if(h == null){
			return new Node(key,value,Color.RED);
		}
		int cmp = key.compareTo(h.key);
		if(cmp < 0){
			h.left = put(h.left, key, value);
		}else if(cmp > 0){
			h.right = put(h.right, key, value);
		}else{
			h.value = value;
		}
		
		h = fix(h);
		
		return h;
	}

	private Node fix(Node h) {
		if(isRed(h.right) && !isRed(h.left)){
			h = rotateLeft(h);
		}
		if(isRed(h.left) && isRed(h.left.left)){
			h = rotateRight(h);
		}
		if(isRed(h.right) && isRed(h.left)){
			flipColor(h);
		}
		return h;
	}
}
