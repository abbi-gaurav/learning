package coursera.alg1.lect4;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BST<K extends Comparable<K>, V> implements SymbolTable<K, V>, Range<K> {
	private Node root;

	private final class Node {
		final K key;
		V value;
		Node left;
		Node right;
		int count;
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	@Override
	public V get(K key) {
		Node x = root;
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp < 0) {
				x = x.left;
			} else if (cmp > 0) {
				x = x.right;
			} else {
				return x.value;
			}
		}
		return null;
	}

	@Override
	public void put(K key, V value) {
		this.root = put(root, key, value);
	}

	private Node put(Node node, K key, V value) {
		if (node == null) {
			Node x = new Node(key, value);
			x.count=1;
			return x;
		}

		int cmp = key.compareTo(node.key);

		if (cmp < 0) {
			node.left = put(node.left, key, value);
		} else if (cmp > 0) {
			node.right = put(node.right, key, value);
		} else {
			node.value = value;
		}
		node.count = 1+size(node.left)+size(node.right);
		
		return node;
	}

	@Override
	public K min() {
		Node x = min(root);
		return x.key;
	}

	private Node min(Node x) {
		while(x.left != null){
			x = x.left;
		}
		return x;
	}

	@Override
	public K max() {
		Node x = root;
		while(x.right != null){
			x = x.right;
		}
		return x.key;
	}

	@Override
	public K floor(K key) {
		Node n = floor(root, key);
		return n == null?null:n.key;
	}

	private Node floor(Node x, K key) {
		if(x == null){
			return null;
		}
		
		int cmp = key.compareTo(x.key);
		
		if(cmp == 0){
			return x;
		}else if(cmp < 0){
			return floor(x.left, key);
		}else{
			Node rightSide = floor(x.right, key);
			return rightSide != null ? rightSide:x;
		}
	}

	@Override
	public K ceiling(K key) {
		Node n = ceiling(root,key);
		return n == null ? null:n.key;
	}

	private Node ceiling(Node x, K key) {
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);
		if (cmp == 0) {
			return x;
		} else if (cmp > 0) {
			return ceiling(x.right, key);
		} else {
			Node leftSide = ceiling(x.left, key);
			return leftSide != null ? leftSide : x;
		}
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(Node x) {
		return x == null ? 0:x.count;
	}
	
	/* (non-Javadoc)
	 * @see coursera.alg1.lect4.SymbolTable#rank(java.lang.Comparable)
	 */
	@Override
	public int rank(K key) {
		return rank(root,key);
	}

	private int rank(Node x, K key) {
		if(x == null){
			return 0;
		}
		int cmp = key.compareTo(x.key);
		if(cmp == 0){
			return size(x.left);
		}else if(cmp < 0){
			return rank(x.left,key);
		}else {
			return 1 + size(x.left)+rank(x.right, key);
		}
	}

	@Override
	public Iterator<K> iterator() {
		Queue<K> queue = new LinkedList<>();
		inOrder(queue, root);
		return queue.iterator();
	}

	private void inOrder(Queue<K> queue, Node x) {
		if(x == null){
			return;
		}
		inOrder(queue, x.left);
		queue.add(x.key);
		inOrder(queue, x.right);
	}
	
	@Override
	public void deleteMin() {
		root = deleteMin(root);
	}

	private Node deleteMin(Node x) {
		if(x.left == null){
			return x.right;
		}
		x.left = deleteMin(x.left);
		x.count = 1+size(x.left)+size(x.right);
		return x;
	}
	
	@Override
	public void deleteMax() {
		root = deleteMax(root);
	}

	private Node deleteMax(Node x) {
		if(x.right == null){
			return x.left;
		}
		x.right = deleteMax(x.right);
		x.count = 1+size(x.left)+size(x.right);
		return x;
	}

	@Override
	public void delete(K key) {
		root = delete(root,key);
	}

	private Node delete(Node x, K key) {
		if (x == null){
			return null;
		}
		
		int cmp = key.compareTo(x.key);
		if(cmp<0){
			x.left =  delete(x.left, key);
		}else if(cmp > 0){
			x.right =  delete(x.right, key);
		}else{
			if(x.right == null){
				return x.left;
			}
			
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		
		x.count = size(x.left)+size(x.right)+1;
		return x;
	}

	@Override
	public K rankBasedSelection(int n) {
		return rankBasedSelection(root,n).key;
	}

	private Node rankBasedSelection(Node x, int n) {
		int sizeLeft = size(x.left);
		if(sizeLeft == n){
			return x;
		} else if(sizeLeft > n){
			return rankBasedSelection(x.left, n);
		}else{
			return rankBasedSelection(x.right, n-sizeLeft-1);
		}
	}

	/* (non-Javadoc)
	 * @see coursera.alg1.lect4.Range#rangeCount(java.lang.Comparable, java.lang.Comparable)
	 */
	@Override
	public int rangeCount(K lo, K high) {
		int count = rank(high)-rank(lo);
		return contains(high) ? count+1:count;
	}

	@Override
	public boolean contains(K key) {
		return get(key) != null;
	}

	@Override
	public Iterable<K> range(K lo, K high) {
		Queue<K> queue = new LinkedList<>();
		return range(root, lo,high,queue);
	}

	private Iterable<K> range(Node x, K lo, K high, Queue<K> queue) {
		int leftCmp = x.key.compareTo(lo);
		int rightCmp = x.key.compareTo(high);
		
		if (leftCmp > 0) {
			if(x.left != null){
				range(x.left, lo, high, queue);
			}
			if(rightCmp <= 0){
				queue.add(x.key);
			}
		}
		

		if (rightCmp < 0) {
			if(x.right != null){
				range(x.right, lo, high, queue);
			}
		}
		return queue;
	}
	
}
