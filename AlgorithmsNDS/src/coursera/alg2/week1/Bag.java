package coursera.alg2.week1;

import java.util.Iterator;
import java.util.LinkedList;

public class Bag<T> implements Iterable<T>{
	private final LinkedList<T> list = new LinkedList<>();
	
	public void add(T item){
		list.add(item);
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	public int size() {
		return list.size();
	}
	
}
