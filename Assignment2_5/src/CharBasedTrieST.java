
@SuppressWarnings("unchecked")
public class CharBasedTrieST<T> {
	private static final int R = 256;
	
	private T[] keys = (T[]) new Object[R];
	
	public void put(char c, T value){
		keys[c] = value;
	}
	
	public T get(char c){
		return keys[c];
	}
}
