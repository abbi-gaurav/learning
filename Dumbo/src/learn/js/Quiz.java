package learn.js;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Quiz {
	public static void main(String[] args) {
		final List<String> list = new ArrayList<String>(){
			private static final long serialVersionUID = -4663776669264662242L;
			{ 
				add("Hello"); 
			}
		};
		final Iterator<String> iterator = list.iterator();
		System.out.println(iterator.next());
		list.add("World");
		// FIXME : work here while I'm sunbathing
		 for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
	            //solution_one              
	            ((ArrayList<String>) list).ensureCapacity(1);
	 
	            //solution_two
//	            ((ArrayList<String>) list).trimToSize();
//	 
//	            //solution_three
//	            if (!(i == Integer.MIN_VALUE) && !(i % 2 == 0)) {
//	                ((ArrayList<String>) list).remove(0);
//	            }
//	            if (i % 2 == 0) {
//	                list.add("World");
//	            }
	        }
		System.out.println(iterator.next());
	}
}
