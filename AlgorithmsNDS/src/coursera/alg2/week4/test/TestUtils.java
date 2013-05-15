package coursera.alg2.week4.test;

import java.util.Iterator;

public class TestUtils {
	public static <T> int count(Iterable<T> itr){
		int count = 0;
		Iterator<T> iterator = itr.iterator();
		
		while(iterator.hasNext()){
			count++;
			iterator.next();
		}
		
		return count;
	}
}
